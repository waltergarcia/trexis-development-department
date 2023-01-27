package net.trexis.software.development.department.util;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum RoleEnum {
    MANAGER("Manager", "",17),
    DEVELOPER_SENIOR("Developer", "Senior", 46) ,

    DEVELOPER_JUNIOR("Developer", "Junior", 36),

    QA_TESTER("QA Tester", "", 20);

    final String value;
    final String level;
    final int cost;

    public static RoleEnum fromValue(final String value) {
        return Arrays.stream(RoleEnum.values())
                .filter(role -> role.value
                        .equalsIgnoreCase(value)).findFirst().orElse(null);
    }

    public static RoleEnum fromLevel(final String level) {
        return Arrays.stream(RoleEnum.values())
                .filter(role -> role.level
                        .equalsIgnoreCase(level)).findFirst().orElse(null);
    }
}
