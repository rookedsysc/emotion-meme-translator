import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

const ip = "backend";
const apiUrl = `http://${ip}:8080`;

export default defineConfig({
  plugins: [react()],
  server: {
	host: true,
    port: 5173,
    proxy: {
      "/api": {
        target: apiUrl,
        changeOrigin: true,
      },
      "/translator": {
        target: apiUrl,
        changeOrigin: true,
      },
    },
  },
});
