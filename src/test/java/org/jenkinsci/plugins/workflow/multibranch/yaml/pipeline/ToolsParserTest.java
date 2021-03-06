package org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline;

import org.apache.commons.io.FileUtils;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.ChildToolModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.PipelineModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.models.ToolsModel;
import org.jenkinsci.plugins.workflow.multibranch.yaml.pipeline.parsers.PipelineParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ToolsParserTest {

    @Before
    public void setup() {
    }

    @Test
    public void toolsSingle() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/tools/toolsSingle.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<ToolsModel> toolsModel = pipelineModel.get().getTools();
        Assert.assertTrue(toolsModel.isPresent());
        Assert.assertEquals(1, toolsModel.get().getChildToolModels().size());
        for(ChildToolModel childToolModel : toolsModel.get().getChildToolModels()){
            Assert.assertEquals("maven", childToolModel.getToolName());
            Assert.assertEquals("maven", childToolModel.getToolType());
        }
    }

    @Test
    public void toolsMulti() throws IOException {
        String jenkinsFileContent = FileUtils.readFileToString(new File("src/test/resources/tools/toolsMulti.yml"));
        PipelineParser pipelineParser  = new PipelineParser(jenkinsFileContent);
        Optional<PipelineModel> pipelineModel = pipelineParser.parse();
        Assert.assertTrue(pipelineModel.isPresent());
        Optional<ToolsModel> toolsModel = pipelineModel.get().getTools();
        Assert.assertTrue(toolsModel.isPresent());
        Assert.assertEquals(2, toolsModel.get().getChildToolModels().size());
        for(ChildToolModel childToolModel : toolsModel.get().getChildToolModels()){
            Assert.assertEquals(childToolModel.getToolType(), childToolModel.getToolName());
        }
    }
}
