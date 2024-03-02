package br.com.eterniaserver.eterniaserver.modules.kit;

import br.com.eterniaserver.eterniaserver.modules.Constants;

import java.util.List;

public class Utils {

    private Utils() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    record CustomKit(int delay, List<String> commands, List<String> messages) {
    }

}
