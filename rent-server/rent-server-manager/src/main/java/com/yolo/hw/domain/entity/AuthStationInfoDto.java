package com.yolo.hw.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AuthStationInfoDto {
    private Long keyId;

    private Date insertTimestamp;

    private Integer handleSource = 2;

    private Integer applyStatus;

    private Long userId;
    private String userNickName;
    private String userMobile;

    private Date optTimestamp;

    private Long handleUserId;

    private String opHandleUser;
    private String opHandleMobile;

    private Long enterpriseId;
    private String enterpriseName;
    private Integer enterpriseType;

    private Long stationId;
    private String stationName;
    private Integer stationType;
}
