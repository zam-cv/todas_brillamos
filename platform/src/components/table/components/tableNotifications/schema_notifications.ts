import {z} from "zod"

export const taskSchema = z.object({
    title: z.string(),
    description: z.string(),
})

export type Task = z.infer<typeof taskSchema>