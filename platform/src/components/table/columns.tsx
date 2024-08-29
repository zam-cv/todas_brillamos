import { useState } from "react";
import { ColumnDef } from "@tanstack/react-table";
import { Checkbox } from "@/components/ui/checkbox";
import { Task } from "./data/schema";
import { DataTableColumnHeader } from "./data-table-column-header";

export const columns: ColumnDef<Task>[] = [
    {
      accessorKey: "username",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Username" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("username")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "firstname",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Firstname" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("firstname")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "lastname",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Lastname" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("lastname")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "team",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Team" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("team")}
            </span>
          </div>
        );
      },
      filterFn: (row, id, value) => {
        return value.includes(row.getValue(id))
      },
    },
    {
      accessorKey: "personal_email",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Personal Email" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("personal_email")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "phone",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Phone" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("phone")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "semester",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Semester" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("semester")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "campus",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Campus" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("campus")}
            </span>
          </div>
        );
      },
      filterFn: (row, id, value) => {
        return value.includes(row.getValue(id))
      },
    },
    {
      accessorKey: "major",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Major" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("major")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "email",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Email" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="max-w-[500px] truncate font-medium">
              {row.getValue("email")}
            </span>
          </div>
        );
      },
    },
    {
      accessorKey: "with_bus",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="With Bus" />
      ),
      cell: ({ row }) => {
        return (
          <div className="flex">
            <Checkbox
              checked={row.getValue("with_bus")}
              aria-label="Confirm"
              className="ml-5"
            />
          </div>
        );
      },
      enableSorting: false,
      enableHiding: false,
      filterFn: (row, id, value) => {
        return value.includes(row.getValue(id))
      },
    }
  ];
  