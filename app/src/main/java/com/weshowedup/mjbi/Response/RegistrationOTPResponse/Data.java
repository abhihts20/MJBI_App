
package com.weshowedup.mjbi.Response.RegistrationOTPResponse;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("otp")
    private Long mOtp;

    public Long getOtp() {
        return mOtp;
    }

    public void setOtp(Long otp) {
        mOtp = otp;
    }

}
