/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vesalainen.test.pom;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tkv
 */
public class VersionRangeTest
{
    
    public VersionRangeTest()
    {
    }

    @Test
    public void testInclusive1()
    {
        VersionRange vr = new VersionRange("[1.0,3.0]");
        assertFalse(vr.in("0.9"));
        assertTrue(vr.in("1.0"));
        assertTrue(vr.in("1.1"));
        assertTrue(vr.in("3.0"));
        assertFalse(vr.in("3.1"));
    }
    
    @Test
    public void testInclusive2()
    {
        VersionRange vr = new VersionRange("[1.0,]");
        assertFalse(vr.in("0.9"));
        assertTrue(vr.in("1.0"));
        assertTrue(vr.in("1.1"));
        assertTrue(vr.in("3.0"));
        assertTrue(vr.in("3.1"));
    }
    
    @Test
    public void testInclusive3()
    {
        VersionRange vr = new VersionRange("[,3.0]");
        assertTrue(vr.in("0.9"));
        assertTrue(vr.in("1.0"));
        assertTrue(vr.in("1.1"));
        assertTrue(vr.in("3.0"));
        assertFalse(vr.in("3.1"));
    }
    
    @Test
    public void testInclusive4()
    {
        VersionRange vr = new VersionRange("[3.0]");
        assertFalse(vr.in("0.9"));
        assertFalse(vr.in("1.0"));
        assertFalse(vr.in("1.1"));
        assertTrue(vr.in("3.0"));
        assertFalse(vr.in("3.1"));
    }
    
    @Test
    public void testExclusive1()
    {
        VersionRange vr = new VersionRange("(1.0,3.0)");
        assertFalse(vr.in("0.9"));
        assertFalse(vr.in("1.0"));
        assertTrue(vr.in("1.1"));
        assertFalse(vr.in("3.0"));
        assertFalse(vr.in("3.1"));
    }
    
    @Test
    public void testExclusive2()
    {
        VersionRange vr = new VersionRange("(1.0,)");
        assertFalse(vr.in("0.9"));
        assertFalse(vr.in("1.0"));
        assertTrue(vr.in("1.1"));
        assertTrue(vr.in("3.0"));
        assertTrue(vr.in("3.1"));
    }
    
    @Test
    public void testExclusive3()
    {
        VersionRange vr = new VersionRange("(,3.0)");
        assertTrue(vr.in("0.9"));
        assertTrue(vr.in("1.0"));
        assertTrue(vr.in("1.1"));
        assertFalse(vr.in("3.0"));
        assertFalse(vr.in("3.1"));
    }
    
}
