package model.classes;

import model.skills.SkillsRepository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class ClassesRepository
{
    private final Map<String, Class> knownClasses = new HashMap<>();

    private final SkillsRepository skillsRepository;

    @Inject
    public ClassesRepository(SkillsRepository skillsRepository)
    {
        this.skillsRepository = skillsRepository;
    }

    public Class getClass(String className) throws IOException
    {
        Class aClass = knownClasses.get(className);
        if(aClass == null)
        {
            aClass = ClassParser.parseClass(skillsRepository, className + ".json");
            knownClasses.put(className, aClass);
        }
        return aClass;
    }

    public List<Class> getAllClasses()
    {
        return new ArrayList<>(knownClasses.values());
    }
}
