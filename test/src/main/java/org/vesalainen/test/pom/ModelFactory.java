/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vesalainen.test.pom;

import java.io.File;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.DefaultModelBuilder;
import org.apache.maven.model.building.DefaultModelBuildingRequest;
import org.apache.maven.model.building.DefaultModelProcessor;
import org.apache.maven.model.building.FileModelSource;
import org.apache.maven.model.building.ModelBuilder;
import org.apache.maven.model.building.ModelBuildingException;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelSource;
import org.apache.maven.model.composition.DefaultDependencyManagementImporter;
import org.apache.maven.model.inheritance.DefaultInheritanceAssembler;
import org.apache.maven.model.interpolation.StringSearchModelInterpolator;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.management.DefaultDependencyManagementInjector;
import org.apache.maven.model.management.DefaultPluginManagementInjector;
import org.apache.maven.model.normalization.DefaultModelNormalizer;
import org.apache.maven.model.path.DefaultModelPathTranslator;
import org.apache.maven.model.path.DefaultModelUrlNormalizer;
import org.apache.maven.model.path.DefaultPathTranslator;
import org.apache.maven.model.path.DefaultUrlNormalizer;
import org.apache.maven.model.plugin.DefaultPluginConfigurationExpander;
import org.apache.maven.model.plugin.DefaultReportConfigurationExpander;
import org.apache.maven.model.plugin.DefaultReportingConverter;
import org.apache.maven.model.profile.DefaultProfileInjector;
import org.apache.maven.model.profile.DefaultProfileSelector;
import org.apache.maven.model.resolution.ModelResolver;
import org.apache.maven.model.resolution.UnresolvableModelException;
import org.apache.maven.model.superpom.DefaultSuperPomProvider;
import org.apache.maven.model.validation.DefaultModelValidator;

/**
 *
 * @author tkv
 */
public class ModelFactory
{
    private final ModelResolver fileModelResolver = new FileModelResolver();
    private final ModelResolver urlModelResolver = new UrlModelResolver();
    
    public Model getGlobalModel(Dependency dependency) throws UnresolvableModelException, ModelBuildingException
    {
        return getGlobalModel(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion());
    }
    public Model getLocalModel(Dependency dependency) throws UnresolvableModelException, ModelBuildingException
    {
        return getLocalModel(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion());
    }
    public Model getGlobalModel(MavenKey key) throws UnresolvableModelException, ModelBuildingException
    {
        return getGlobalModel(key.getGroupId(), key.getArtifactId(), key.getVersion());
    }
    public Model getLocalModel(MavenKey key) throws UnresolvableModelException, ModelBuildingException
    {
        return getLocalModel(key.getGroupId(), key.getArtifactId(), key.getVersion());
    }
    public Model getGlobalModel(String groupId, String artifactId, String version) throws UnresolvableModelException, ModelBuildingException
    {
        ModelSource modelSource = urlModelResolver.resolveModel(groupId, artifactId, version);
        return getGlobalModel(modelSource);
    }
    public Model getLocalModel(String groupId, String artifactId, String version) throws UnresolvableModelException, ModelBuildingException
    {
        FileModelSource fileModelSource = (FileModelSource) fileModelResolver.resolveModel(groupId, artifactId, version);
        return getLocalModel(fileModelSource);
    }
    public Model getGlobalModel(ModelSource modelSource) throws ModelBuildingException
    {
        return getModel(modelSource, urlModelResolver);
    }
    public Model getLocalModel(ModelSource modelSource) throws ModelBuildingException
    {
        return getModel(modelSource, fileModelResolver);
    }
    public Model getModel(ModelSource modelSource, ModelResolver modelResolver) throws ModelBuildingException
    {
        ModelBuildingRequest req = new DefaultModelBuildingRequest();
        req.setProcessPlugins(false);
        req.setModelSource(modelSource);
        req.setModelResolver(modelResolver);
        req.setValidationLevel(ModelBuildingRequest.VALIDATION_LEVEL_MINIMAL);

        DefaultModelProcessor modelProcessor = new DefaultModelProcessor()
                .setModelReader(new DefaultModelReader());
        DefaultSuperPomProvider superPomProvider = new DefaultSuperPomProvider()
                .setModelProcessor(modelProcessor);
        DefaultPathTranslator pathTranslator = new DefaultPathTranslator();
        DefaultUrlNormalizer urlNormalizer = new DefaultUrlNormalizer();
        StringSearchModelInterpolator stringSearchModelInterpolator = (StringSearchModelInterpolator) new StringSearchModelInterpolator()
                .setPathTranslator(pathTranslator)
                .setUrlNormalizer(urlNormalizer);
        DefaultModelUrlNormalizer modelUrlNormalizer = new DefaultModelUrlNormalizer()
                .setUrlNormalizer(urlNormalizer);
        DefaultModelPathTranslator modelPathTranslator = new DefaultModelPathTranslator()
                .setPathTranslator(pathTranslator);
        ModelBuilder builder = new DefaultModelBuilder()
                .setDependencyManagementImporter(new DefaultDependencyManagementImporter())
                .setDependencyManagementInjector(new DefaultDependencyManagementInjector())
                .setInheritanceAssembler(new DefaultInheritanceAssembler())
                .setModelNormalizer(new DefaultModelNormalizer())
                .setModelPathTranslator(modelPathTranslator)
                .setModelProcessor(modelProcessor)
                .setModelUrlNormalizer(modelUrlNormalizer)
                .setModelValidator(new DefaultModelValidator())
                .setPluginConfigurationExpander(new DefaultPluginConfigurationExpander())
                .setPluginManagementInjector(new DefaultPluginManagementInjector())
                .setProfileInjector(new DefaultProfileInjector())
                .setProfileSelector(new DefaultProfileSelector())
                .setReportConfigurationExpander(new DefaultReportConfigurationExpander())
                .setReportingConverter(new DefaultReportingConverter())
                .setSuperPomProvider(superPomProvider)
                .setModelInterpolator(stringSearchModelInterpolator)
                ;
        return builder.build(req).getEffectiveModel();
    }
}
