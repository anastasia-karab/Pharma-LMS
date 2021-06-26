package pharma.lms.PharmaLMS.user.domain;

public enum Department {
    ООК ("Отдел Обеспечения Качества"),
    ОКК ("Отдел Контроля Качества"),
    Лаборатория ("Лаборатория"),
    Производство ("Производство"),
    Отдел_кадров ("Отдел кадров"),
    Документация ("Документация"),
    Высшее_руководство ("Высшее руководство");

    private final String departmentName;

    Department(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}
