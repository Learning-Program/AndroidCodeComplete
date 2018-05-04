package code.complete.log.printer;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import code.complete.log.LogInfo;
import code.complete.log.LogPrinter;

/**
 * Printer集合
 * @author imkarl
 */
public final class PrinterSet implements LogPrinter {

    private final LogPrinter[] printerArray;

    private PrinterSet(Set<LogPrinter> printers) {
        if (printers == null || printers.isEmpty()) {
            this.printerArray = null;
            return;
        }

        this.printerArray = new LogPrinter[printers.size()];
        printers.toArray(printerArray);
    }

    @NonNull
    public static PrinterSet wrap(Collection<LogPrinter> printers) {
        if (printers == null || printers.isEmpty()) {
            return new PrinterSet(null);
        }
        return new PrinterSet(new HashSet<>(printers));
    }
    @NonNull
    public static PrinterSet wrap(LogPrinter... printers) {
        if (printers == null || printers.length == 0) {
            return new PrinterSet(null);
        }
        return new PrinterSet(new HashSet<>(Arrays.asList(printers)));
    }

    @Override
    public void println(@NonNull LogInfo infos, @NonNull String message) {
        if (printerArray == null) {
            return;
        }
        for (LogPrinter printer : printerArray) {
            printer.println(infos, message);
        }
    }
}
