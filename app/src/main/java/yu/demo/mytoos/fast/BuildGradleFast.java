package yu.demo.mytoos.fast;

public class BuildGradleFast implements IFast{

    @Override
    public String getTargetPath () {
        return "build.gradle";
    }

    @Override
    public String getModifyLineStart () {
        return "classpath 'com.android.tools.build:gradle:";
    }

    @Override
    public String getModifyLineContent () {
        return "3.2.0'";
    }
}
