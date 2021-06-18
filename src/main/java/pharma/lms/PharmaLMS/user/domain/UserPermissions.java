package pharma.lms.PharmaLMS.user.domain;

public enum UserPermissions {
    COURSE_ADD("course:add"),
    COURSE_READ("course:read"),
    PRESENTATION_ADD("presentation:add"),
    PRESENTATION_READ("presentation:read");

    private final String permission;

    UserPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
