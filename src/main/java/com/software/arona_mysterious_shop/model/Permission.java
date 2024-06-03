package com.software.arona_mysterious_shop.model;


import lombok.Data;

import java.util.List;

@Data
public class Permission {
    private boolean publicView;

    private List<String> sensitiveFields;
}
