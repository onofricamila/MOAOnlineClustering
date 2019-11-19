package data;

import com.yahoo.labs.samoa.instances.Attribute;
import com.yahoo.labs.samoa.instances.DenseInstance;
import com.yahoo.labs.samoa.instances.Instances;
import com.yahoo.labs.samoa.instances.InstancesHeader;
import java.util.ArrayList;
import java.util.Random;

public class InstanceWithHeaderGenerator {
    public static DenseInstance randomInstance(int size) {
        // generates the name of the features which is called as InstanceHeader
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();
        for (int i = 0; i < size; i++) {
            attributes.add(new Attribute("feature_" + i));
        }
        // create instance header with generated feature name
        InstancesHeader streamHeader = new InstancesHeader(
                new Instances("Mustafa Çelik Instance",attributes, size));

        // generates random data
        double[] data = new double[2];
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            data[i] = random.nextDouble();
        }

        // creates an instance and assigns the data
        DenseInstance inst = new DenseInstance(1.0, data);

        // assigns the instanceHeader(feature name)
        inst.setDataset(streamHeader);

        return inst;
    }
}
