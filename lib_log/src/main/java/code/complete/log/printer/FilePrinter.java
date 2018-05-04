package code.complete.log.printer;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import code.complete.log.LogInfo;
import code.complete.log.LogPrinter;

/**
 * 输出到文件的Printer
 * @author imkarl
 */
public final class FilePrinter implements LogPrinter {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());

    private final BufferedWriter writer;

    public FilePrinter(File file, boolean append) throws IOException {
        writer = new BufferedWriter(new FileWriter(file, append));
    }

    @Override
    public void println(@NonNull LogInfo infos, @NonNull String message) {
        StringBuilder line = new StringBuilder();
        // time
        line.append(DATE_FORMAT.format(new Date())).append(' ');
        // level
        switch (infos.getPriority()) {
            case Log.VERBOSE:
                line.append("VERBOSE");
                break;
            case Log.DEBUG:
                line.append("DEBUG");
                break;
            case Log.INFO:
                line.append("INFO");
                break;
            case Log.WARN:
                line.append("WARN");
                break;
            case Log.ERROR:
                line.append("ERROR");
                break;
            case Log.ASSERT:
                line.append("ASSERT");
                break;
            default:
                break;
        }
        line.append(' ');
        // tag
        line.append(infos.getTag()).append('\t');
        // code line
        final StackTraceElement element = infos.getCallerStackTrace()[0];
        String className = element.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1);
        final String codeLine = className+'.'+element.getMethodName()+'('+element.getFileName()+':'+element.getLineNumber()+')';
        line.append(codeLine).append('\t');
        // thread
        line.append("[Thread: ").append(infos.getThreadName()).append(']');

        // message
        line.append('\n').append('\t');
        line.append(message);

        line.append('\n');

        try {
            writer.write(line.toString());
        } catch (IOException e) {
            Log.w(infos.getTag(), e);
        }
    }

}
