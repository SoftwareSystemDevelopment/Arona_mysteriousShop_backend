package com.software_system_development.arona_mysterious_shop_backend.model;


import lombok.Data;

import java.util.List;

@Data
public class Permission {
    private boolean publicView;

    private List<String> sensitiveFields;
}
