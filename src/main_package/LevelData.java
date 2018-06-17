package main_package;

public class LevelData {

    private String[] level3 = new String[]{    // 30*60 x 12*60 = 1800x720
            "111111111111111111111111111111",
            "100000000000000000000000000001",
            "100020000000000000000000002001",
            "101110000000200000000000011001",
            "100000000000100000000111000001",
            "100002000002000000000000000111",
            "100000000000111111000000000001",
            "100000000000000000000111110001",
            "100000000100000001111100000001",
            "100000001120000010000000000001",
            "100000000000000010000000000001",
            "111111111111111111111111111111",
    };

    private String[] level2 = new String[]{    // 30*60 x 12*60 = 1800x720
            "111111111111111111111111111111",
            "100000000000000000000000000001",
            "100020000000000000000000002001",
            "101110000000200000000000011001",
            "111111111111111000000111000001",
            "100002000002000000000000000111",
            "100001000000111111000000000001",
            "100001000000000000000111110001",
            "100001000100000001111100000001",
            "100000011100200010000000000001",
            "100000000000000010000000000001",
            "111111111111111111111111111111",
    };

    private String[] level1 = new String[]{    // 30*60 x 12*60 = 1800x720
            "111111111111111111111111111111",
            "120000000111111000000000000201",
            "100000000000011000000000000201",
            "100000000001100000000000020111",
            "111000010001100000000011111111",
            "100000010001111100000000000111",
            "100001110001100000000000000111",
            "100000010001100011100000000111",
            "110000010001100000000000000111",
            "100001110001100000000011111111",
            "100000010000000000000000000111",
            "111111111111111111111111111111",
    };

    public String[] getLevel2() {
        return level2;
    }

    public String[] getLevel1() {
        return level1;
    }
}
