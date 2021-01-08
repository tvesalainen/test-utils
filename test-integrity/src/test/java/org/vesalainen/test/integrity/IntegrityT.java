/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vesalainen.test.integrity;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.maven.model.resolution.UnresolvableModelException;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.vesalainen.test.pom.IntegrityTest;

/**
 *
 * @author Timo Vesalainen <timo.vesalainen@iki.fi>
 */
public class IntegrityT
{
    
    @Test
    public void check()
    {
        try
        {
            IntegrityTest test = new IntegrityTest("org.vesalainen");
            test.check("org.vesalainen.nmea", "router", "1.8.1");
            //test.check("fi.hoski", "parent", "1.0.7");
            assertTrue(test.report());
        }
        catch (UnresolvableModelException ex)
        {
            fail(ex.getMessage());
            Logger.getLogger(IntegrityT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
