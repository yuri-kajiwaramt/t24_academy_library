package jp.co.metateam.library.model;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jp.co.metateam.library.values.RentalStatus;
import lombok.Getter;
import lombok.Setter;
import java.util.Optional;



/**
 * 貸出管理DTO
 */
@Getter
@Setter
public class RentalManageDto {

    private Long id;

    @NotEmpty(message="在庫管理番号は必須です")
    private String stockId;

    @NotEmpty(message="社員番号は必須です")
    private String employeeId;

    @NotNull(message="貸出ステータスは必須です")
    private Integer status;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message="貸出予定日は必須です")
    @FutureOrPresent(message= "貸出予定日は現在より後を指定してください")
    private Date expectedRentalOn;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message="返却予定日は必須です")
    @FutureOrPresent(message = "返却予定日は現在よりも後を指定してください")
    private Date expectedReturnOn;

    private Timestamp rentaledAt;

    private Timestamp returnedAt;

    private Timestamp canceledAt;

    private Stock stock;

    private Account account;

    public Optional<String> isValidStatus(Integer preStatus) {
        String errorMessage ="貸し出しステータスを%sから%sに編集することはできません。";
    
        if (preStatus.equals(RentalStatus.RENT_WAIT.getValue())) {
            if (this.status.equals(RentalStatus.RETURNED.getValue())) {
                return Optional.of(String.format(errorMessage, RentalStatus.RENT_WAIT.getText(), RentalStatus.RETURNED.getText()));
            }
        } else if (preStatus.equals(RentalStatus.RENTAlING.getValue())) {
            if (this.status.equals(RentalStatus.RENT_WAIT.getValue())) {
                return Optional.of(String.format(errorMessage, RentalStatus.RENTAlING.getText(), RentalStatus.RENT_WAIT.getText()));
            } else if (this.status.equals(RentalStatus.CANCELED.getValue())) {
                return Optional.of(String.format(errorMessage, RentalStatus.RENTAlING.getText(), RentalStatus.CANCELED.getText()));
            }
        } else if (preStatus.equals(RentalStatus.CANCELED.getValue())) {
            if (this.status.equals(RentalStatus.RENT_WAIT.getValue())) {
                return Optional.of(String.format(errorMessage, RentalStatus.CANCELED.getText(), RentalStatus.RENT_WAIT.getText()));
            } else if (this.status.equals(RentalStatus.RENTAlING.getValue())) {
                return Optional.of(String.format(errorMessage, RentalStatus.CANCELED.getText(), RentalStatus.RENTAlING.getText()));
            } else if (this.status.equals(RentalStatus.RETURNED.getValue())) {
                return Optional.of(String.format(errorMessage, RentalStatus.CANCELED.getText(), RentalStatus.RETURNED.getText()));
            }
        } else if (preStatus.equals(RentalStatus.RETURNED.getValue())) {
            if (this.status.equals(RentalStatus.RENT_WAIT.getValue())) {
                return Optional.of(String.format(errorMessage, RentalStatus.RETURNED.getText(), RentalStatus.RENT_WAIT.getText()));
            } else if (this.status.equals(RentalStatus.RENTAlING.getValue())) {
                return Optional.of(String.format(errorMessage, RentalStatus.RETURNED.getText(), RentalStatus.RENTAlING.getText()));
            } else if (this.status.equals(RentalStatus.CANCELED.getValue())) {
                return Optional.of(String.format(errorMessage, RentalStatus.RETURNED.getText(), RentalStatus.CANCELED.getText()));
            }
        }
        return Optional.empty();
    }
}