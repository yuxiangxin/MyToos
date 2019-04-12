package yu.demo.mytoos.fast;

public class GradlePropertiesFast implements IFast {
    @Override
    public String getTargetPath () {
        return "gradle.properties";
    }

    @Override
    public String getModifyLineStart () {
        return "org.gradle.jvmargs";
    }

    @Override
    public String getModifyLineContent () {
        return "=-Xmx800m";
    }
}
