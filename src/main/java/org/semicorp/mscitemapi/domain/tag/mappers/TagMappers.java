package org.semicorp.mscitemapi.domain.tag.mappers;
import org.semicorp.mscitemapi.domain.tag.Tag;
import java.util.List;

public class TagMappers {


    public List<Tag> stringListToTagList(List<String> stringList) {
        for(String string : stringList) {
            Tag.builder()
                    .build();
        }

        return null;
    }

}
