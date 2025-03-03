import { getConfig } from "./auth";
import { API_URL } from "./constants";
import axios from "axios";

export async function post<T, B>(path: string, body: B, withConfig = true) {
  return axios
    .post(`${API_URL}${path}`, body, withConfig ? getConfig() : undefined)
    .then(({ data }: { data: T }) => data);
}

export async function get<T>(path: string, withConfig = true) {
  return axios
    .get(`${API_URL}${path}`, withConfig ? getConfig() : undefined)
    .then(({ data }: { data: T }) => data);
}

export async function del<T>(path: string, withConfig = true) {
  return axios
    .delete(`${API_URL}${path}`, withConfig ? getConfig() : undefined)
    .then(({ data }: { data: T }) => data);
}

export async function upload<T>(path: string, file: File, metadata?: Object, withConfig = true) {
  const formData = new FormData();
  formData.append("file", file);
  if (metadata) {
    formData.append("metadata", JSON.stringify(metadata));
  }

  const config: any = withConfig ? getConfig() : undefined;

  return axios
    .post(`${API_URL}${path}`, formData, config)
    .then(({ data }: { data: T }) => data);
}