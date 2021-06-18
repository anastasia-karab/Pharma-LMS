package pharma.lms.PharmaLMS.user.domain;

public enum Department {
    ООК ("Отдел Обеспечения Качества"),
    ОКК ("Отдел Контроля Качества"),
    Лаборатория ("Лаборатория"),
    Производство ("Производство"),
    Отдел_кадров ("Отдел кадров"),
    Докмуентация ("Документация"),
    Высшее_руководство ("Высшее руководство");

    private final String departemntName;

    Department(String departemntName) {
        this.departemntName = departemntName;
    }

    public String getDepartemntName() {
        return departemntName;
    }
}
