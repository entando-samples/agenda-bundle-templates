const colors = require('tailwindcss/colors')

module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {},
  },
  daisyui: {
    logs: false,
    themes: [{
      entando: {
        "primary": "#22c55e",
        "secondary": "#201A23",
        "accent": "#76b1d3",
        "neutral": "#201A23",
        "base-100": "#FFFFFF",
        "info": "#319FE3",
        "success": "#55E7B4",
        "warning": "#FBC851",
        "error": "#EF6E62",
        ".btn-primary": {
          'color': '#FFFFFF',
        },
        "div.dropdown > label > label > button": {
          'background-color': '#888888',
          'border-color': '#777777'
        },
        "div.dropdown > label > label > button:hover": {
          'background-color': '#22c55e',
          'border-color': '#22c55e'
        },
      },
    }, "light", "dark"],
    styled: true,
    base: true,
    utils: true,
    rtl: false,
  },
  plugins: [require("daisyui")],
}
