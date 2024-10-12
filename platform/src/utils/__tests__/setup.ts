// src/utils/__tests__/setup.ts

// Mock window object
Object.defineProperty(global, 'window', {
    value: {
      location: {
        hostname: 'localhost', // Set to your desired hostname
      },
    },
    writable: true,
  });
  