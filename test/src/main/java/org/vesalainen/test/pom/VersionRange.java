/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vesalainen.test.pom;

/**
 *
 * @author tkv
 */
public class VersionRange
{
    private Bound lowerBound;
    private Bound upperBound;
    
    public VersionRange(String version)
    {
        if (!isVersionRange(version))
        {
            throw new IllegalArgumentException(version+" is not version range");
        }
        char start = version.charAt(0);
        char end = version.charAt(version.length()-1);
        String bound = version.substring(1, version.length()-1);
        String[] ss = bound.split(",");
        switch (count(',', bound))
        {
            case 0:
                if (start == '(')
                {
                    throw new IllegalArgumentException(version+" doesn't make any sense");
                }
                else
                {
                    lowerBound = new InclusiveLowerBound(bound);
                }
                if (end == ')')
                {
                    throw new IllegalArgumentException(version+" doesn't make any sense");
                }
                else
                {
                    upperBound = new InclusiveUpperBound(bound);
                }
            break;
            case 1:
                if (ss.length == 1)
                {
                    if (bound.charAt(0) == ',')
                    {
                        lowerBound = new AnyBound();
                        if (end == ')')
                        {
                            upperBound = new ExclusiveUpperBound(ss[0]);
                        }
                        else
                        {
                            upperBound = new InclusiveUpperBound(ss[0]);
                        }
                    }
                    else
                    {
                        upperBound = new AnyBound();
                        if (start == '(')
                        {
                            lowerBound = new ExclusiveLowerBound(ss[0]);
                        }
                        else
                        {
                            lowerBound = new InclusiveLowerBound(ss[0]);
                        }
                    }
                }
                else
                {
                    if (start == '(')
                    {
                        lowerBound = new ExclusiveLowerBound(ss[0]);
                    }
                    else
                    {
                        lowerBound = new InclusiveLowerBound(ss[0]);
                    }
                    if (end == ')')
                    {
                        upperBound = new ExclusiveUpperBound(ss[1]);
                    }
                    else
                    {
                        upperBound = new InclusiveUpperBound(ss[1]);
                    }
                }
            break;
        }
    }
    
    public boolean in(String version)
    {
        return lowerBound.in(version) && upperBound.in(version);
    }
    
    public static boolean isVersionRange(String version)
    {
        if (version.length() <= 4)
        {
            return false;
        }
        char start = version.charAt(0);
        char end = version.charAt(version.length()-1);
        return (start=='[' || start=='(') && (end==']' || end==')');
    }

    private static int count(char cc, String str)
    {
        int count = 0;
        int idx = str.indexOf(cc);
        while (idx != -1)
        {
            count++;
            idx = str.indexOf(cc, idx+1);
        }
        return count;
    }
    private abstract class Bound
    {

        public Bound()
        {
        }
        protected abstract boolean in(String version);
    }
    private class AnyBound extends Bound
    {

        @Override
        protected boolean in(String version)
        {
            return true;
        }
        
    }
    private abstract class LowerBound extends Bound
    {
        protected String bound;

        public LowerBound(String bound)
        {
            this.bound = bound;
        }
    }
    private class InclusiveLowerBound extends LowerBound
    {

        public InclusiveLowerBound(String bound)
        {
            super(bound);
        }

        @Override
        protected boolean in(String version)
        {
            return bound.compareTo(version) <= 0;
        }
        
    }
    private class ExclusiveLowerBound extends LowerBound
    {

        public ExclusiveLowerBound(String bound)
        {
            super(bound);
        }

        @Override
        protected boolean in(String version)
        {
            return bound.compareTo(version) < 0;
        }
        
    }
    private abstract class UpperBound extends Bound
    {
        protected String bound;

        public UpperBound(String bound)
        {
            this.bound = bound;
        }
    }
    private class InclusiveUpperBound extends UpperBound
    {

        public InclusiveUpperBound(String bound)
        {
            super(bound);
        }

        @Override
        protected boolean in(String version)
        {
            return bound.compareTo(version) >= 0;
        }
        
    }
    private class ExclusiveUpperBound extends UpperBound
    {

        public ExclusiveUpperBound(String bound)
        {
            super(bound);
        }

        @Override
        protected boolean in(String version)
        {
            return bound.compareTo(version) > 0;
        }
    }
}
