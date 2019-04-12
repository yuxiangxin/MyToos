package yu.demo.mytoos.fast;

public class GradleWrapperPropertiesFast implements IFast {
    @Override
    public String getTargetPath () {
        return "gradle/wrapper/gradle-wrapper.properties";
    }

    @Override
    public String getModifyLineStart () {
        return "distributionUrl=https\\://services.gradle.org/distributions/";
    }

    @Override
    public String getModifyLineContent () {
        return "gradle-4.10.1-all.zip";
    }
}
