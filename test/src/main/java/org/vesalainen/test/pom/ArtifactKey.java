/*
 * Copyright (C) 2017 Timo Vesalainen <timo.vesalainen@iki.fi>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.vesalainen.test.pom;

import java.util.Objects;
import org.apache.maven.model.Dependency;

/**
 *
 * @author Timo Vesalainen <timo.vesalainen@iki.fi>
 */
public class ArtifactKey
{
    private String groupId;
    private String artifactId;
    private String type;

    public ArtifactKey(Dependency dependency)
    {
        this(dependency.getGroupId(), dependency.getArtifactId(), dependency.getType());
    }

    public ArtifactKey(String groupId, String artifactId, String type)
    {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.type = type;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public String getArtifactId()
    {
        return artifactId;
    }

    public String getType()
    {
        return type;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.groupId);
        hash = 89 * hash + Objects.hashCode(this.artifactId);
        hash = 89 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final ArtifactKey other = (ArtifactKey) obj;
        if (!Objects.equals(this.groupId, other.groupId))
        {
            return false;
        }
        if (!Objects.equals(this.artifactId, other.artifactId))
        {
            return false;
        }
        if (!Objects.equals(this.type, other.type))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return groupId + ":" + artifactId + ":" + type;
    }

}
