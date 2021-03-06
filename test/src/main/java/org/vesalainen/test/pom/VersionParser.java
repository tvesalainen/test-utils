/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vesalainen.test.pom;

import org.vesalainen.parser.GenClassFactory;
import org.vesalainen.parser.annotation.GenClassname;
import org.vesalainen.parser.annotation.GrammarDef;
import org.vesalainen.parser.annotation.ParseMethod;
import org.vesalainen.parser.annotation.Rule;
import org.vesalainen.parser.annotation.Terminal;

/**
 *
 * @author Timo Vesalainen <timo.vesalainen@iki.fi>
 */
@GenClassname("org.vesalainen.test.pom.VersionParserImpl")
@GrammarDef()
public abstract class VersionParser
{
    public static VersionParser VERSION_PARSER = getInstance();
    
    public static VersionParser getInstance()
    {
        return (VersionParser) GenClassFactory.loadGenInstance(VersionParser.class);
    }

    @ParseMethod(start="versionRange")
    public abstract VersionRange parseVersionRange(String text);

    @ParseMethod(start="version")
    public abstract Version parseVersion(String text);

    @Rule("rangeII")
    @Rule("rangeIE")
    @Rule("rangeEI")
    @Rule("rangeEE")
    @Rule("rangeIL")
    @Rule("rangeEL")
    @Rule("rangeIU")
    @Rule("rangeEU")
    @Rule("rangeOnly")
    @Rule("rangeAny")
    protected abstract VersionRange versionRange(VersionRange versionRange);
            
    @Rule("'\\[' version '\\,' version '\\]'")
    protected VersionRange rangeII(Version v1, Version v2)
    {
        return new VersionRange((v)->v.compareTo(v1)>=0 && v.compareTo(v2)<=0, "["+v1+","+v2+"]");
    }
    @Rule("'\\[' version '\\,' version '\\)'")
    protected VersionRange rangeIE(Version v1, Version v2)
    {
        return new VersionRange((v)->v.compareTo(v1)>=0 && v.compareTo(v2)<0, "["+v1+","+v2+")");
    }
    @Rule("'\\(' version '\\,' version '\\]'")
    protected VersionRange rangeEI(Version v1, Version v2)
    {
        return new VersionRange((v)->v.compareTo(v1)>0 && v.compareTo(v2)<=0, "("+v1+","+v2+"]");
    }
    @Rule("'\\(' version '\\,' version '\\)'")
    protected VersionRange rangeEE(Version v1, Version v2)
    {
        return new VersionRange((v)->v.compareTo(v1)>0 && v.compareTo(v2)<0, "("+v1+","+v2+")");
    }
    @Rule("'\\[' version '\\,[\\)\\]]'")
    protected VersionRange rangeIL(Version v1)
    {
        return new VersionRange((v)->v.compareTo(v1)>=0, "["+v1+",)");
    }
    @Rule("'\\(' version '\\,[\\)\\]]'")
    protected VersionRange rangeEL(Version v1)
    {
        return new VersionRange((v)->v.compareTo(v1)>0, "("+v1+",)");
    }
    @Rule("'[\\[\\(]\\,' version '\\]'")
    protected VersionRange rangeIU(Version v1)
    {
        return new VersionRange((v)->v.compareTo(v1)<=0, "(,"+v1+"]");
    }
    @Rule("'[\\[\\(]\\,' version '\\)'")
    protected VersionRange rangeEU(Version v1)
    {
        return new VersionRange((v)->v.compareTo(v1)<0, "(,"+v1+")");
    }
    @Rule("'\\[' version '\\]'")
    protected VersionRange rangeOnly(Version v1)
    {
        return new VersionRange((v)->v.compareTo(v1)==0, "["+v1+"]");
    }
    @Rule("version")
    protected VersionRange rangeAny(Version v1)
    {
        return new VersionRange((v)->true, v1.toString(), v1);
    }
    @Rule("integer '\\.' integer '\\.' integer '\\-' string")
    protected Version version(int major, int minor, int incremental, String qualifier)
    {
        Version version = new Version()
                .setMajor(major)
                .setMinor(minor)
                .setIncremental(incremental)
                .setQualifier(qualifier);
        return version;
    }
    
    @Rule("integer '\\.' integer '\\-' string")
    protected Version version(int major, int minor, String qualifier)
    {
        Version version = new Version()
                .setMajor(major)
                .setMinor(minor)
                .setQualifier(qualifier);
        return version;
    }
    
    @Rule("integer '\\-' string")
    protected Version version(int major, String qualifier)
    {
        Version version = new Version()
                .setMajor(major)
                .setQualifier(qualifier);
        return version;
    }
    
    @Rule("integer '\\.' integer '\\.' integer")
    protected Version version(int major, int minor, int incremental)
    {
        Version version = new Version()
                .setMajor(major)
                .setMinor(minor)
                .setIncremental(incremental);
        return version;
    }
    
    @Rule("integer '\\.' integer")
    protected Version version(int major, int minor)
    {
        Version version = new Version()
                .setMajor(major)
                .setMinor(minor);
        return version;
    }
    
    @Rule("integer")
    protected Version version(int major)
    {
        Version version = new Version()
                .setMajor(major);
        return version;
    }
    
    @Terminal(expression="[0-9]+")
    protected abstract int integer(int x);
    
    @Terminal(expression="[0-9a-zA-z\\-]+")
    protected abstract String string(String s);
    
}
