/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vesalainen.test.pom;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.ModelBuildingException;
import org.apache.maven.model.resolution.UnresolvableModelException;

/**
 *
 * @author tkv
 */
public class IntegrityTest
{
    private final Set<MavenKey> checked = new HashSet<>();
    private final Set<MavenKey> notInCentral = new TreeSet<>();
    private final ModelFactory modelFactory = new ModelFactory();
    private final String[] prefixes;

    public IntegrityTest(String... prefixes)
    {
        this.prefixes = prefixes;
    }
    
    public void report()
    {
        System.err.println("Not in Central Registry:");
        for (MavenKey key : notInCentral)
        {
            List<MavenKey> attachment = key.getAttachment();
            if (attachment == null)
            {
                System.err.println(key);
            }
            else
            {
                System.err.print(key);
                System.err.print(" ");
                System.err.println(attachment);
            }
        }
    }
    public void check(String groupId, String artifactId, String version) throws UnresolvableModelException
    {
        Deque<MavenKey> stack = new ArrayDeque<>();
        stack.push(new MavenKey(groupId, artifactId, version, "pom"));
        check(stack);
        assert stack.size() == 1;
    }
    public void check(Deque<MavenKey> stack) throws UnresolvableModelException
    {
        MavenKey key = stack.peek();
        Model model = null;
        try
        {
            model = modelFactory.getGlobalModel(key);
            checkDependencies(model, stack);
        }
        catch (ModelBuildingException ex)
        {
            try
            {
                key.attach(stack);
                notInCentral.add(key);
                model = modelFactory.getLocalModel(key);
                checkDependencies(model, stack);
            }
            catch (ModelBuildingException ex1)
            {
                throw new IllegalArgumentException(ex1.getMessage()+" "+stack, ex);
            }
        }
    }
    private void checkDependencies(Model model, Deque<MavenKey> stack)
    {
        if ("pom".equals(model.getPackaging()))
        {
            DependencyManagement dependencyManagement = model.getDependencyManagement();
            if (dependencyManagement != null)
            {
                for (Dependency dependency : dependencyManagement.getDependencies())
                {
                    stack.push(new MavenKey(dependency));
                    checkDependency(dependency, stack);
                    stack.pop();
                }
            }
        }
        for (Dependency dependency : model.getDependencies())
        {
            stack.push(new MavenKey(dependency));
            checkDependency(dependency, stack);
            stack.pop();
        }
    }
    private void checkDependency(Dependency dependency, Deque<MavenKey> stack)
    {
        MavenKey key = stack.peek();
        if (myPrefix(dependency.getGroupId()) && !checked.contains(key))
        {
            checked.add(key);
            Model model = null;
            try
            {
                model = modelFactory.getGlobalModel(dependency);
                checkDependencies(model, stack);
            }
            catch (UnresolvableModelException | ModelBuildingException ex)
            {
                try
                {
                    key.attach(stack);
                    notInCentral.add(key);
                    model = modelFactory.getLocalModel(dependency);
                    checkDependencies(model, stack);
                }
                catch (UnresolvableModelException | ModelBuildingException ex1)
                {
                    throw new IllegalArgumentException(ex1.getMessage()+" "+stack, ex);
                }
            }
        }
    }
    private boolean myPrefix(String groupId)
    {
        for (String prefix : prefixes)
        {
            if (groupId.startsWith(prefix))
            {
                return true;
            }
        }
        return false;
    }
}
