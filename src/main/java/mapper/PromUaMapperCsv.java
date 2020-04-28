package mapper;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class PromUaMapperCsv extends BaseMapper {
    @Override
    public Object[] map(Reader reader) {

        Object[] map = super.map(reader);
        List<Object[]> result = new ArrayList<>();
            for (Object lineObj : map) {
                String[] splitLine = this.splitLineByComa((String) lineObj);
                result.add(new Object[]{
                        splitLine[0].trim() // site
                });
            }
            return result.toArray();

    }


}
