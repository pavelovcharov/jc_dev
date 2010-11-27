package omegaCommander.threads.newThreads;

import omegaCommander.fileSystem.AbsoluteFile;
import omegaCommander.fileSystem.FileHelper;

class CopyHelper {

    public static String GetCopyTarget(AbsoluteFile[] files, AbsoluteFile source, String path) {
        boolean oneFile = files.length == 1;
        boolean isDir = files[0].isDirectory();
        String result = path;
        if (!FileHelper.isAbsolutePath(path)) {
            result = source.getPathWithSlash() + result;
        }
        if (oneFile) {
            if (isDir) {

            } else {
            }
        } else {
        }
        return result;
    }
}
