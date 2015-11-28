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
 * @author tkv
 */
public class TestIntegrity
{
    
    @Test
    public void check()
    {
        try
        {
            IntegrityTest test = new IntegrityTest("org.vesalainen", "fi.hoski");
            test.check("org.vesalainen", "parent", "1.0.8");
            test.check("fi.hoski", "parent", "1.0.6");
            assertTrue(test.report());
        }
        catch (UnresolvableModelException ex)
        {
            fail(ex.getMessage());
            Logger.getLogger(TestIntegrity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
