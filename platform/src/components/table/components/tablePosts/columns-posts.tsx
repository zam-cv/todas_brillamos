import { ColumnDef } from "@tanstack/react-table";
import { Task } from "@/components/table/components/tablePosts/schema_posts";
import { DataTableColumnHeader } from "./data-table-column-header-posts";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"
import { Posts } from "@/utils/api/post";
import { useState } from "react";
import { set } from "zod";

export const createColumns = (
  handleDelete: (postId: number) => void,
  updatePost: (value: object, fields: Posts) => void): ColumnDef<Task>[] => [
  {
    accessorKey: "title",
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="Título" />
    ),
    cell: ({ row }) => {
      const [title, setTitle] = useState<string>(row.getValue("title"));
      function handleSave(value: string) {
        updatePost({title : value}, (row.original as any));
        setTitle(value);
      }


      return (
        <div className="flex space-x-2 outline-none font-semibold bg-transparent  " contentEditable={true}>
        <input
          name = "title"
          value={title}
          onChange = {(e) => handleSave(e.target.value)}
          className="bg-transparent outline-none font-semibold"
          
          >
        </input>
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
      const [author, setAuthor] = useState<string>(row.getValue("author"));
      function handleSave(value: string) {
        updatePost({author : value}, (row.original as any));
        setAuthor(value);
      }
      return (
        <div className="flex space-x-2 outline-none font-semibold bg-transparent  " contentEditable={true}>
        <input
          name = "author"
          value={author}
          onChange = {(e) => handleSave(e.target.value)}
          className="bg-transparent outline-none font-semibold"
          
          >
        </input>
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
      const [date, setDate] = useState<string>(row.getValue("date")); 
      function handleSave(value: string) {
        updatePost({date : value}, (row.original as any));
        setDate(value);
      }
      return (
        <div className="flex space-x-2 outline-none font-semibold bg-transparent  " contentEditable={true}>
        <input
          name = "date"
          value={date}
          onChange = {(e) => handleSave(e.target.value)}
          className="bg-transparent outline-none font-semibold"
          
          >
        </input>
      </div>
      );
    },
  },
  {
    accessorKey: "content",
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="Contenido" />
    ),
    cell: ({ row }) => {
      const [content, setContent] = useState<string>(row.getValue("content"));
      function handleSave(value: string) {
        updatePost({content : value}, (row.original as any));
        setContent(value);
      }
      return (
        <div className="flex space-x-2 outline-none font-semibold bg-transparent  " contentEditable={true}>
        <input
          name = "content"
          value={content}
          onChange = {(e) => handleSave(e.target.value)}
          className="bg-transparent outline-none font-semibold"
          
          >
        </input>
      </div>
      );
    },
  },
  {
    id: "actions",
    header: "Acciones",
    cell: ({ row }) => {
      return (
        <div className="flex ">
            <Dialog>
              <DialogTrigger asChild>
                <Button variant="destructive" className="size-fit">Eliminar</Button>
              </DialogTrigger>
              <DialogContent className="sm:max-w-[425px]">
                  <DialogHeader>
                    <DialogTitle>Eliminar Artículo</DialogTitle>
                    <DialogDescription>
                      Esta acción no se puede deshacer.
                    </DialogDescription>
                  </DialogHeader>
                  <DialogFooter>
                  <Button 
                    variant="destructive" 
                    onClick={() => handleDelete((row.original as any).id)}
                  >
                    Eliminar
                  </Button>
                  </DialogFooter>
              </DialogContent>
            </Dialog>
          </div>
      );
    },
  },
];