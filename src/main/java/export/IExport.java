package export;

import java.io.File;
import java.util.List;

public interface IExport {
    void export(File file, List<ExportRow> rows, String pageTitle);
}
