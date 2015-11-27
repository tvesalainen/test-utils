/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vesalainen.test.pom;

import java.util.ArrayList;
import java.util.List;
import org.apache.maven.model.Repository;
import org.apache.maven.model.resolution.InvalidRepositoryException;
import org.apache.maven.model.resolution.ModelResolver;

/**
 *
 * @author tkv
 */
public abstract class AbstractModelResolver implements ModelResolver
{
    protected final List<Repository> list = new ArrayList<>();

    public AbstractModelResolver()
    {
    }

    protected String getFilename(String groupId, String artifactId, String version)
    {
        return String.format("%s/%s/%s/%s-%s.pom", groupId.replace('.', '/'), artifactId, version, artifactId, version);
    }
    protected String getDirectory(String groupId, String artifactId)
    {
        return String.format("%s/%s", groupId.replace('.', '/'), artifactId);
    }
    @Override
    public void addRepository(Repository repository) throws InvalidRepositoryException
    {
        String id = repository.getId();
        for (Repository r : list)
        {
            if (id.equals(r.getId()))
            {
                return;
            }
        }
        list.add(repository);
    }
    
}
