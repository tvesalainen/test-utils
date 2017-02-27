/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vesalainen.test.pom;

import java.util.Objects;

/**
 *
 * @author tkv
 */
public class Version implements Comparable<Version>
{
    private int major = -1;
    private int minor = -1;
    private int incremental = -1;
    private String qualifier;
    private int buildNumber = -1;

    public int getMajor()
    {
        return major;
    }

    public Version setMajor(int major)
    {
        this.major = major;
        return this;
    }

    public int getMinor()
    {
        return minor;
    }

    public Version setMinor(int minor)
    {
        this.minor = minor;
        return this;
    }

    public int getIncremental()
    {
        return incremental;
    }

    public Version setIncremental(int incremental)
    {
        this.incremental = incremental;
        return this;
    }

    public String getQualifier()
    {
        return qualifier;
    }

    public Version setQualifier(String qualifier)
    {
        int idx = qualifier.lastIndexOf('-');
        if (idx != -1)
        {
            this.qualifier = qualifier.substring(0, idx);
            this.buildNumber = Integer.parseInt(qualifier.substring(idx+1));
        }
        else
        {
            this.qualifier = qualifier;
        }
        return this;
    }

    public int getBuildNumber()
    {
        return buildNumber;
    }

    public Version setBuildNumber(int buildNumber)
    {
        this.buildNumber = buildNumber;
        return this;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 59 * hash + this.major;
        hash = 59 * hash + this.minor;
        hash = 59 * hash + this.incremental;
        hash = 59 * hash + Objects.hashCode(this.qualifier);
        hash = 59 * hash + this.buildNumber;
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
        final Version other = (Version) obj;
        if (this.major != other.major)
        {
            return false;
        }
        if (this.minor != other.minor)
        {
            return false;
        }
        if (this.incremental != other.incremental)
        {
            return false;
        }
        if (this.buildNumber != other.buildNumber)
        {
            return false;
        }
        if (!Objects.equals(this.qualifier, other.qualifier))
        {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Version o)
    {
        if (major == o.major)
        {
            if (minor == o.minor)
            {
                if (incremental == o.incremental)
                {
                    if (Objects.equals(qualifier, o.qualifier))
                    {
                        return buildNumber - o.buildNumber;
                    }
                    else
                    {
                        if (qualifier == null)
                        {
                            return -1;
                        }
                        if (o.qualifier == null)
                        {
                            return 1;
                        }
                        return qualifier.compareTo(o.qualifier);
                    }
                }
                else
                {
                    return incremental - o.incremental;
                }
            }
            else
            {
                return minor - o.minor;
            }
        }
        else
        {
            return major - o.major;
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(major);
        if (minor != -1)
        {
            sb.append('.').append(minor);
        }
        if (incremental != -1)
        {
            sb.append('.').append(incremental);
        }
        if (qualifier != null)
        {
            sb.append('-').append(qualifier);
        }
        if (buildNumber != -1)
        {
            sb.append('-').append(buildNumber);
        }
        return sb.toString();
    }
    
}
