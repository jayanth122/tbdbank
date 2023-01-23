package org.ece.util;

import lombok.Getter;
import lombok.Setter;
import org.ece.dto.AccessType;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class AccessManager {
    private AccessType accessLevel;
    private String userName;
}
