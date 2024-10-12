// src/utils/__tests__/global.d.ts
export {};

declare global {
  interface Window {
    location: {
      hostname: string;
    };
  }
}
