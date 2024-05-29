import { plugin } from 'postcss'

/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  theme: {
    extend: {},
  },
  plugins: [require("daisyui")],
}

module.exports = {
  content: ["node_modules/daisyui/dist/**/*.js", "node_modules/react-daisyui/dist/**/*.js"],
  plugins: [require("daisyui")],
  daisyui: {
    themes: [
      {
        custom: {
          "primary": "#09050A",
          "primary-content": "#f3f4f6",
          "secondary": "#3A193C",
          "secondary-content": "#f5f5f4",
          "accent": "#C37AD2",
          "accent-content": "#111827",
          "neutral": "#210F23",
          "neutral-content": "#cec9ce",
          "base-100": "#ffffff",
          "base-200": "#dedede",
          "base-300": "#bebebe",
          "base-content": "#161616",
          "info": "#fb923c",
          "info-content": "#150801",
          "success": "#a3e635",
          "success-content": "#0a1301",
          "warning": "#facc15",
          "warning-content": "#150f00",
          "error": "#be185d",
          "error-content": "#f5f5f4",
        },
      },
    ],
    styled: true, // include daisyUI colors and design decisions for all components
    prefix: "", // prefix for daisyUI classnames (components, modifiers and responsive class names. Not colors)
    logs: true, // Shows info about daisyUI version and used config in the console when building your CSS
    themeRoot: ":root", // The element that receives theme color CSS variables
  },
}
