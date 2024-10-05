import { useRef } from "react"
import { Button } from "@/components/ui/button"
import { Trash2 } from "lucide-react"
import {
  Drawer,
  DrawerClose,
  DrawerContent,
  DrawerDescription,
  DrawerFooter,
  DrawerHeader,
  DrawerTitle,
  DrawerTrigger,
} from "@/components/ui/drawer"

export function DeleteWrapper({
  del,
  message,
  children,
  className
}: {
  del: () => void
  message: string
  children: React.ReactNode,
  className?: string
}) {
  const cancelRef = useRef<HTMLButtonElement>(null);

  function Cancel() {
    cancelRef.current?.click();
  }

  return <Drawer>
    <DrawerTrigger asChild>
      <div className={className}>
        {children}
      </div>
    </DrawerTrigger>
    <DrawerContent>
      <div className="mx-auto w-full max-w-sm">
        <DrawerHeader>
          <DrawerTitle>
            {message}
          </DrawerTitle>
          <DrawerDescription>
            Esta acción no se puede deshacer.
          </DrawerDescription>
        </DrawerHeader>
        <DrawerFooter>
          <Button onClick={() => { del(); Cancel() }}>
            Delete
          </Button>
          <DrawerClose asChild>
            <Button ref={cancelRef} variant="outline">Cancel</Button>
          </DrawerClose>
        </DrawerFooter>
      </div>
    </DrawerContent>
  </Drawer>
}

export default function Delete(
  {
    del,
    message
  }: {
    del: () => void
    message: string
  }) {
  return <DeleteWrapper del={del} message={message}>
    <span className="bg-white rounded-md shadow-m group/trash cursor-pointer">
      <div className="p-2">
        <Trash2 className="w-5 h-5 group-hover/trash:text-red-500" />
      </div>
    </span>
  </DeleteWrapper>
}