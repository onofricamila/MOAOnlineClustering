package data;

import com.yahoo.labs.samoa.instances.DenseInstance;

public class InstanceGenerator {
    public static DenseInstance randomInstance(int size) {
        DenseInstance instance = new DenseInstance(size);
        for (int idx = 0; idx < size; idx++) {
            instance.setValue(idx, Math.random());
        }
        return instance;
    }
}
