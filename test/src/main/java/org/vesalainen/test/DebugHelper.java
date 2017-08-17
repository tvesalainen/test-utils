/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vesalainen.test;

import java.lang.management.ManagementFactory;

/**
 * Debug helpers
 * @author Timo Vesalainen <timo.vesalainen@iki.fi>
 */
public class DebugHelper
{
    /**
     * Tries to guess if running in debugger
     * @return 
     */
    public static final boolean guessDebugging()
    {
        for (String arg : ManagementFactory.getRuntimeMXBean().getInputArguments())
        {
            if (arg.contains("jdwp"))
            {
                return true;
            }
        }
        return false;
    }
}
