import { Table } from "@tanstack/react-table";
import { DataTableViewOptions } from "./data-table-view-options-notifications";
//import api from "@/utils/api";

interface DataTableToolbarProps<TData> {
  table: Table<TData>;
  event_id: number;
}

export function DataTableToolbar<TData>({
  table,
  
}: DataTableToolbarProps<TData>) {
  

/*
  useEffect(() => {
    api.events.getTeamsNames(event_id).then(setTeams);
    api.tec.getCampus().then(setCampuses);
  }, [event_id]);
*/
  return (
    <div className="flex items-center justify-between">
      <div className="flex flex-1 items-center space-x-2">
       
      </div>
      <DataTableViewOptions table={table} />
    </div>
  );
}
