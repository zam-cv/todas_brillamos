import { Cross2Icon } from "@radix-ui/react-icons";
import { Table } from "@tanstack/react-table";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { DataTableViewOptions } from "./data-table-view-options-shipments";
import { DataTableFacetedFilter } from "./data-table-faceted-filter-shipments";
import { useEffect, useState } from "react";
//import api from "@/utils/api";

interface DataTableToolbarProps<TData> {
  table: Table<TData>;
  event_id: number;
}

export function DataTableToolbar<TData>({
  table,
  event_id,
}: DataTableToolbarProps<TData>) {
  const [teams, setTeams] = useState<string[]>([]);
  const [campuses, setCampuses] = useState<string[]>([]);
  const isFiltered = table.getState().columnFilters.length > 0;

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
