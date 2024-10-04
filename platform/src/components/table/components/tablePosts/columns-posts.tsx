import { useState } from "react";
import { ColumnDef } from "@tanstack/react-table";
import { Checkbox } from "@/components/ui/checkbox";
import { Task } from "@/components/table/components/tablePosts/schema_posts";
import { DataTableColumnHeader } from "./data-table-column-header-posts";

export const columns: ColumnDef<Task>[] = [
    {
        accessorKey: "title",
        header: ({ column }) => (
          <DataTableColumnHeader column={column} title="Título" />
        ),
        cell: ({ row }) => {
          return (
            <div className="flex space-x-2">
              <span className="max-w-[500px] truncate font-medium">
                {row.getValue("title")}
              </span>
            </div>
          );
        },
      },
    {
      accessorKey: "author",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Autor" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("author")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "date",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Fecha" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("date")}
            </span>
          </div>
        );
      },
    },
    
    
  ];
  