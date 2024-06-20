package org.example.bean;

public enum Role {
    CUSTOMER((byte) 0), COOK((byte) 1), ADMIN((byte) 2);

    private final byte roleByte;

    Role(byte roleByte) {
        this.roleByte = roleByte;
    }

    public byte toByte() {
        return roleByte;
    }

    public static Role fromByte(byte roleByte) {
        for (Role role : Role.values()) {
            if (role.roleByte == roleByte) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role byte: " + roleByte);
    }

    public static void main(String[] args) {
        byte roleByte = 1; // 假设角色字节值为1，代表厨师
        Role role = Role.fromByte(roleByte);
        System.out.println("Converted role: " + role);
    }
}
