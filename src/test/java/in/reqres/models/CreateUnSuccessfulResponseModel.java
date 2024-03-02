package in.reqres.models;

import lombok.Data;

@Data
public class CreateUnSuccessfulResponseModel {
    String email, error;
}