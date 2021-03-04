/* 
 * Copyright (C) 2021 Fawzi Aiboud Nygren & Victor Wallsten <3
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


// When document is ready, execute script
window.addEventListener('load', () =>{
  // Gets element with ID canvas
  const canvas = document.querySelector('#canvas');
  // Defines context to work within, 2D in this case
  const context = canvas.getContext('2d');
  
  // Initialize size
  canvas.height = window.innerHeight*(1/3);
  canvas.width = window.innerWidth*(1/3);
  
  // Initialize paint
  let drawing = false;
  
  function startPosition (e) {
    drawing = true;
    draw(e);
  }
  
  function endPosition () {
    drawing = false;
    context.beginPath();
  }
  
  function draw (e) {
    if (!drawing) return;
    
    // Pen styles
    context.lineWidth = 3.5;
    context.lineCap = 'round';
    
    context.lineTo(e.clientX - canvas.offsetLeft, e.clientY - canvas.offsetTop);
    context.stroke();
    context.beginPath();
    context.moveTo(e.clientX - canvas.offsetLeft, e.clientY - canvas.offsetTop);
  }

  // Mouse EventListers
  canvas.addEventListener('mousedown', startPosition);
  canvas.addEventListener('mouseup', endPosition);
  canvas.addEventListener('mousemove', draw);
  canvas.addEventListener('mouseleave', endPosition);
});

function loadCanvas(){
  const canvas = document.getElementById('judgeCanvas');
  const context = canvas.getContext('2d');
  
  //Load img from dataUrl
  const imageObj = new Image();
  
  imageObj.onload = function (){
    context.drawImage(this, 0, 0);
  };
  imageObj.src = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAvoAAAFRCAYAAAAFLAllAAAgAElEQVR4nO3de1zUdb7H8R8DM8NtyKBQQAXFC15AuXjLAPPCJbU00zWrsz3OSW177Ob2WNvUOuVlzaxOZWlubWp1UvdYnTLTct3cU20GOKAR9pAeaZBKK7iINIssCe/zR8c5al7QsB/zm9fz8Zh/ziPhM8yex+M1M9+LYfwfSSMkrZH0laQmAQAAAPAVZfq+5YdLsp8M/DBJ8yQdNHEwAAAAAD+eR9IsSR0MSZMl7TF3HgAAAABtxCNpgqHvP+JnqQ4AAABgHUsMSW+aPQUAAACANvUHQ9LbZk8BAAAAoE39J6EPAAAAWA+hDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ8AAABYEKEPAAAAWBChDwAAAFgQoQ9Y1YkTJ1RXV6eKigrt2rVL27dv1xtvvKEXX3xRjz/+uObNm6fp06drzJgx6tWrl7p376709HRlZWVpxIgRF/3IzMxUamqqunXrpoSEBKWmpiozM/Oi/v3AgQMVGxurDh06KDY2Vv369VOvXr0UGRmp8PBwRUZGqnPnzkpMTFTXrl3VtWtXJSYmKjY2VpGRkcrMzNQbb7yh+vp6s//8AACYjdAH2qvm5mb94x//UFVVlT7//HPt2LFDW7Zs0bp167Ry5UotWbJE999/v+68807l5OSoV69eiouLU+fOnRUVFSWn06mAgAAZhuE3D6fTqbFjx6qkpMTslw8AALMR+oBZmpqaVFlZqY0bN+qee+7RwIED1blzZ3Xt2lUdO3ZUaGiobDab6fHsa48+ffpozZo1Onr0qNkvMQAAZiL0gZ/CyajfvHmzFi1apIkTJyoxMVFOp9OUGA4ICJDT6VRUVJTi4uIUExOjhIQEn1y60717d1199dWy2+2nfapfXFxs9ssOAICZCH2grf1UUW+z2RQaGqro6Gh16dJFcXFx6t27t/Ly8jRjxgzNmzdPjz/+uF588UW98cYb2r59u3bt2qWKigodO3ZMJ06cMPtP1WaKi4uVm5srh8OhgIAAXXvttfrkk0/MHgsAADMR+sCP5fF4VFRUpPnz5ystLU0REREKDAy8qGgPDAyUy+VSYmKi8vPzNX36dN1///1asmSJVq5cqXXr1mnLli3asWOHPv/8c1VVVcnj8ai5udnsp98uVFVV6ZFHHlF8fLwMw1BKSopee+01eTwes0cDAMAshD5wsTwej9xut5YtW6ZJkyYpPj7eu2ykNQ+n06nExERNnDhRixYt0ubNm1VZWammpiazn5rP8ng8ev3115WSkiLDMBQbG6u5c+eqoqLC7NEAADALoQ+0xj/+8Q+9++67ysnJkcvlavUmWaL+p1NQUKCsrCzZbDYFBwdr3LhxnL4DAPBnhD5wIY2Njdq6dauGDx+uoKCg866Z79ixo66//no9/PDDeuedd4j6n9CePXt0++23KyIiQg6HQ7m5uXK73WaPBQCAWQh94Hxqa2v10ksvKS0t7QeRb7fb1bVrV02aNEnLli2T2+1mTbiJysrKNG3aNLlcLrlcLk2bNk1lZWVmjwUAgFkIfeB8du/erYkTJyo0NNS7aTYpKUkLFy4k7NuZUz/Rj4iI0K233kroAwD8GaEPnE9hYaF33XdAQIDS09O1ZcsWNTY2mj0azkDoAwBwGkIfOJ+CggJlZmbKZrPJ6XQqPz+fi5jaqdLSUk2ZMkXh4eEKCwvT5MmTVVpaavZYAACYhdAHzoUjG32L2+1WXl6eHA6HdzMub8oAAH6M0AfOhUuYfAuhDwDAaQh94FyKi4uVm5srh8Mhm82mzMxMFRQUmD0WzoGlOwAAnIbQB86mublZH374oYYMGSLDMBQVFaWZM2eqvLzc7NFwDp999pmmTp2q8PBwhYeHa8qUKYQ+AMCfEfrA2VRXV2vZsmVKTEyUYRjq3r27nnrqKR0+fNjs0XAObrfb+w0MS3cAACD0gbMqLi5Wfn6+HA6HDMPQ4MGD9cEHH6i5udns0XAObrdbOTk5stvtnJAEAAChD/xQY2OjtmzZovT0dBmGoWHDhmnHjh1mj4ULOPXNWYcOHXTHHXfo888/N3ssAADMQugDZzp48KAWLlyorl27yjAM9erVSytXrtSRI0fMHg3nUVRUpJEjRyooKEidOnXS7NmztX//frPHAgDALIQ+cKZdu3Zp3LhxCg4OVnBwsMaPH6+SkhKzx8J5tLS06OOPP9awYcNkGIY6d+6shx56SF9//bXZowEAYBZCHzhVVVWVli5dqoSEBBmGoS5dumjBggU6cOCA2aPhPJqamrRt2zYNGjRIhmEoISFBjz76qKqqqsweDQAAsxD6wKl27dqlG264QcHBwTIMQ2lpaXr33Xf1z3/+0+zRcB4NDQ3auHGjUlNTZRiGevTooWeeeUbV1dVmjwYAgFkIfeBUp57cYrfbNXr0aO3cudPssXAB9fX1Wr9+vZKTk2UYhpKSkrRq1SodPXrU7NEAADALoQ+cqqysTLfeeqsiIiIUERGhW2+9VWVlZWaPhQuora3VqlWr1KdPHxmGof79+2v9+vX69ttvzR4NAACzEPrAqfbs2aPbb7/dG/q33XYboe8Dqqur9cwzz6hHjx4yDEMDBw7Uxo0bdfz4cbNHAwDALIQ+cCo+0fdNVVVVevTRR72bqDMyMrRt2zZ99913Zo8GAIBZCH3gVDt37tSYMWO8a/THjBkjt9tt9li4gK+//loPPfSQOnfuLMMwNHToUH388cdmjwUAgJkIfeCkk0c0ZmRkyDAMderUSffddx+XLvmA/fv3a/bs2erUqZOCgoI0cuRIFRUVmT0WAABmIvSBk85c/hEfH68lS5ZwFrsP+OKLL3TXXXcpKipKTqdTY8eOVXFxsdljAQBgJkIfOKm0tFRTpkxReHi47Ha7cnJyWLbjIz7//HPdcccd6tChA8eiAgDwPUIfkCSPx6MNGzZ4z2GPiYnRnDlzVFFRYfZoaIXCwkJlZmbKZrPxiT4AAN8j9AFJqqio0Jw5cxQTEyPDMJScnKwNGzbI4/GYPRouwOPx6PXXX1dKSooMw1Dnzp310EMP6euvvzZ7NAAAzEToA9LpN+KGh4frZz/7mUpLS80eC63wzTff6LHHHvPurUhJSdHrr7/OmzQAgL8j9IGamhqtWLHCe9lSQkKCli5dyiZcH1FeXq6ZM2cqMjKS9fkAAPw/Qh8oKSnR2LFj5XQ6T7tsqampyezR0AqnfhsTFRWlmTNnqry83OyxAAAwG6EPFBQUeDdyOhwO5ebmspHTR5y5Pv/ktzHffPON2aMBAGA2Qh/+7cxQjI+P1yOPPKJDhw6ZPRpa4ZtvvtHSpUvVrVs31ucDAHA6Qh/+raqqSkuWLDltI+drr71GKPqI8vJyzZgxg/X5AAD8EKEP/1ZcXKzc3Fw5HA7ZbDZlZmaqoKDA7LHQSm63W2PGjPGuz58xYwbr8wEA+B6hD//13Xff6f3331dGRoYMw9CwYcP0ySefmD0WWon1+QAAnBehD//1t7/9TU888YR3fXePHj307LPPqrq62uzR0ApVVVV65JFHFB8fz7IrAAB+iNCH/yorK9Ntt92miIgIBQUFadSoUazv9iGnrs9n2RUAAD9A6MM/NTQ06K233tLAgQNlGIY6duyo2bNna//+/WaPhlZobm7Whx9+qCFDhsgwDDmdTuXn53MsKgAA/4/Qh386cOCA5s+fry5dusgwDPXt21evvvqqjh07ZvZoaIUzbzPu3r27nnrqKR0+fNjs0QAAaC8Iffin3bt368Ybb1RISIhCQkJ04403ateuXWaPhVYqLy/XXXfdpaioKAUEBGjo0KH68MMP1dzcbPZoAAC0F4Q+/M+xY8f06quvql+/fjIMQ126dNHDDz+sAwcOmD0aWuHksp1hw4YpICBAUVFRmjlzJsdqAgBwOkIf/mf//v2aPXu2OnXqJMMwNGDAAL355ptqaGgwezS0Qk1NjZYvX66ePXvKMAz17NlTy5cvV01NjdmjAQDQnhD68D87d+7UqFGjFBQUpIiICN12220qKyszeyy0Unl5uWbOnMmyHQAAzo/Qh385cuSInnvuOe+nwd26ddMTTzzBJUs+4uSynaFDh7JsBwCA8yP04V+Ki4uVn58vh8MhwzCUkZGh999/X999953Zo6EVDh48qEWLFqlr164s2wEA4PwIffiPhoYGbdy4UampqTIMg0+DfVBJSYnGjx+v4OBgGYahIUOG6IMPPmDZDgAAP0Tow39UVFRo7ty5io2NlWEY6t27t/7whz+otrbW7NHQSm63Wzk5ObLb7bLb7Ro9ejS3GQMAcHaEPvxDS0uLCgoKdN111ykoKEhhYWG6+eab9emnn5o9Gi5CWVmZpk2bJpfLJZfLpalTp+qzzz4zeywAANojQh/+4W9/+5ueeOIJdevWjU24PmzPnj26/fbbFRERIbvdrpycHLndbrPHAgCgPSL04R9KS0s1efJkhYWFKTAwUCNGjFBBQYFaWlrMHg0XoaCgQJmZmbLZbAoODtb48eO50RgAgLMj9GF9f//73/XCCy+od+/eMgxDsbGxmjdvnioqKsweDRehvr5e69evV3JysgzDUFxcnB544AFVVlaaPRoAAO0RoQ/rKykp0fXXXy+n0ynDMDRw4EBt3LhRx48fN3s0XIQzj9YcMGCA3nrrLW40BgDg7Ah9WF9BQYGGDx+ugIAAORwO5ebmqri42OyxcJE+//xz/cu//IuuuOIKBQcHa9y4cSopKTF7LAAA2itCH9ZWV1enV155RX379vVuwn388cfZhOtjWlpa9NFHH2no0KEyDEPR0dGaNWuWvvzyS7NHAwCgvSL0YW2VlZV68MEHFRcXJ8MwlJqaqnfeeUeNjY1mj4aLcPToUa1evVpJSUneG3FXrFjBjbgAAJwboQ9rO/W0nbCwME2aNImz833QmZedpaena+vWrWpqajJ7NAAA2itCH9bV2NioLVu2KC0t7bRTWjhtx/ec+YZt8uTJvGEDAOD8CH1Y18lLsrp37y7DMNS3b1+98sorqqurM3s0XISmpiZt3bpV6enpHI8KAEDrEfqwruLiYuXl5cnhcCggIEDDhw9XQUGB2WPhItXU1GjFihXq2bOnDMNQUlKSVq9eraNHj5o9GgAA7RmhD2s6c9nOsGHDtGPHDrPHwiXYt2+ffv3rXys6OloBAQEaOnSoPvroIzU3N5s9GgAA7RmhD2s6ePCgFi5cqC5dunhPaXnuued05MgRs0fDRSopKdG4ceMUHBysK664Qrfffrv27Nlj9lgAALR3hD6sadeuXd44DA4O1vjx47lcyQc1NDTorbfe0sCBA2UYhrp06aKFCxfq4MGDZo8GAEB7R+jDeo4cOaLnn39evXr18sbhggULdODAAbNHw0U6dOiQFi9erPj4eBmGof79+2v9+vX69ttvzR4NAID2jtCH9RQXFys/P18Oh0OGYSgtLU3vvvuu/vnPf5o9Gi5SSUmJrr/+ejmdTgUEBOjaa6/VJ5980iY/u6WlRbW1tdq2bZvuvfdepaWlKTExUYMGDVJ2drZGjBhx0Y/MzEylpqaqW7duSkhIUGpqqjIzM1v93w4fPvy0/1tKSoqSk5MVGxursLAwBQcHq2/fvvqP//gPbncGAFwIoQ9raWpq0rZt25SRkSHDMBQVFaWZM2eqvLzc7NFwCQoLC5Wdna3AwEA5HA7l5eXJ7XZf0s9qbGxUeXm51q1bp3vuuUdDhgxReHi4DMPwqUdAQID69Omj1atXq7a2to3/4gAACyH0YS1VVVV69NFHlZCQwCZcH3fm+flxcXF66KGH9PXXX7fq33s8HhUVFWn+/PlKTU2Vy+WSzWYzPdTb4hEaGqqbbrpJu3fvvsyvAgDAhxH6sJbS0lJNmTJF4eHhstvtysnJ0c6dO80eC5egtrZWq1evVlJSUqvOz/d4PHK73Vq2bJkmTZqk+Ph42e32VoVzYGCgwsLC1LlzZ2VkZLS7pTsxMTEKCQnxvlGx2+0aPXo0/9sGAJwPoQ/r8Hg82rBhg5KTk2UYhmJiYjRnzhx99dVXZo+GS3DmEanp6enaunWrmpqaJP24sA8PD9eQIUN0zz33aN26ddq7d6+OHz9u8jM+P7fbrZycHNntdkIfANAahD6so6KiQnPmzFFMTIwMw1BycrI2bNggj8dj9mi4SCeP1UxLS1NAQIACAwOVnZ2trVu3av369crMzFR4eHirluLYbDZ16tRJ48eP1yOPPKL/+Z//UW1trVpaWsx+mhfl5H4Fm83m/XsUFhaaPRYAoP0i9GEdp37iGR4erp/97GcqLS01eyxcglPvQTAMQ06nU4mJicrIyFBkZOR5w95ut6tr166aNGmSli1bJrfbbYk3e3yiDwC4SIQ+rKG6ulrPPPOMevToIcMwlJCQoKVLl6qqqsrs0XCRqqurtWzZMiUmJp520oy/hf2ZTt1/EhYWpsmTJ/NGFgBwPoQ+rOHUTzsNw1BGRoa2bdvmXc8N37Fr1y7deOONCgkJOWfcOxwOpaSkaMGCBdq5c6clw/5Me/bs0e23366IiAhFRETotttuU1lZmdljAQDaL0Ifvq+qqkpLlizx3p4aHByscePGqaSkxOzRcAkKCgqUmZl51vX3kZGRmjp1qrZs2aJjx46ZPepPitAHAFwkQh++7cyTdtiE6/t27dqlG264wbs+3zAMRUdH6+c//7k2b958zuM1rY7QBwBcJEIfvq2kpOS0TZvx8fFasmSJDh06ZPZouET19fV66623NHbsWF1zzTVaunSpvvrqKzU3N5s9mqnKyso0bdo0uVwuuVwu3XLLLfrss8/MHgsA0H4R+vBd9fX1Wrt2rfr37+9dt52bmyu32232aECbO3UfisPhUF5enoqLi80eCwDQfhH68F179+7V9OnTvcctJiUl6cUXX1Rtba3ZowFtrqioSNddd50CAwPldDo1duxY9qEAAM6H0IdvOnr0qNasWaOkpCTvJs0ZM2Zo7969Zo8GtLmmpiZt3bpVGRkZMgxDHTt21OzZs7V//36zRwMAtF+EPnxTcXGx8vPz5XA4ZBiG+vXrp1dffdXvTmKBf6itrdWqVavUp08f77dXq1ev9tuNyQCAViH04XvOvByLZQywuoMHD2rhwoXq2rWrDMNQenq6tm7dyj0RAIDzIfThW1paWlRUVKT8/Hw5nU4ZhqH+/ftr/fr1qq+vN3s84LI4dT+K0+lUfn4+G3EBABdC6MO3VFZW6oEHHlBcXJwMw1BcXJzmzZunyspKs0cDLpuSkhJdf/31cjqdioyM1PTp09mPAgC4EEIfF3bixAnV1dVp3759Kigo0KZNm7R69WotXbpUs2bN0tixY5WUlKTu3bsrPT1dWVlZGjFixFkfmZmZSk1NVbdu3ZSQkKCUlBQNGDBA8fHx6tixo66++mp16NBBISEhcjqdioiIUGRkpKKiopSamqr8/HzFxsZ6l+zk5+erqKhILS0tZv+ZgMvi5Ebc9PR0GYahLl26aMGCBTpw4IDZowEA2jdCH99rampSZWWlNm7cqFmzZmngwIGKjY1Vx44dFRYWJpvN5r2ltL08evTooWeffVbV1dVm//mAy6a2tlarV6/2njCVlJSkVatWsREXAHAhhL6/qamp0Zo1azRmzBj16NFDvXr1UlxcnFwulwIDA02P99Y+bDabMjMzVVBQYPafFLisKisr9eCDD3qXq7ERFwDQSoS+v/n000910003KTQ0tE1iOzQ0VFdffbU6duyohISENl+643K5FBERIbvdftrvDgkJ0YQJE7R7926z/6TAZVVSUqKxY8fK6XQqMDBQ2dnZKiwsNHssAED7R+j7m+LiYuXm5nrPnz/bIzAwUC6XSz179tSECRN07733aunSpVq9erU2bdqkgoIC7du3T3V1dTpx4sRln9nj8eidd97R6NGj1aFDB1155ZW69tprtWrVKtXU1Fz23w+YaefOnRo5cqSCgoK8+1LcbrfZYwEA2j9C39+43W6NGTPG+wm53W5XVFSUBgwYoLvvvltvvfWWKisrWRYAtAMtLS3asWOHhg0bJsMw1KlTJ913333ciAsAaA1C39+cuXQnNDRUEydOZAkM0A41NDRo48aNGjhwoAzDUPfu3fX000/r8OHDZo8GAGj/CH1/c/IEj379+ikgIEAOh0O5ublcvgO0Q7W1tfrDH/7gPXGnX79+Wrt2LZfDAQBag9D3R5999pmmTp2q8PBwuVwuTZs2TWVlZWaPBeAMhw4d0iOPPKL4+HgZhqGMjAxt27aNpXUAgNYg9P1RaWmppkyZovDwcIWFhWny5MkqLS01eywAZ/jyyy81a9YsRUdHy263a/To0dq5c6fZYwEAfAOh74/cbrfy8vLkcDhYugO0Y2VlZZo2bZpcLhffvgEALhah748IfcA37Ny503tKVnR0tGbNmqUvv/zS7LEAAL6B0PdHLN0B2r+mpiZt27ZNGRkZMgxD8fHx+t3vfqdDhw6ZPRoAwDcQ+v6I5QBA+1dfX6+1a9eqf//+MgxDvXv31gsvvKC///3vZo8GAPANhL4/crvd3ttxIyIidOuttxL6QDtTXV2tp59+WomJiTIMQwMGDNCbb76phoYGs0cDAPgGQt8flZSUaNy4cQoODpbNZlNmZqYKCwvNHgvAKSoqKnT//fcrJiZGhmFo2LBh+vjjj80eCwDgOwh9f1RRUaF58+YpNjZWhmEoJSVFr7/+ujwej9mjAfg/e/fu1fTp0xUZGanAwEBdd911vCEHAFwMQt8feTwevf7660pJSZFhGEpISNDSpUtVVVVl9mgA/k9hYaEyMzNls9nkdDp1/fXXczoWAOBiEPr+yu12e4/ti4qK0syZM7V3716zxwKgH74Z79KlixYsWKADBw6YPRoAwHcQ+v6qvLxcM2bMUGRkJDduAu3MN998o8cee0wJCQksrwMAXCpC31998803Wrp06Wkh8dprrxESQDtQXl6umTNn8kYcAPBjEPr+6sylAbGxsZo7d64qKirMHg3we263Wzk5Od6ldTNmzFB5ebnZYwEAfAuh788KCgq8m/2Cg4M1btw4lZSUmD0W4NfOtVn+m2++MXs0AIBvIfT92akbch0Oh3Jzc+V2u80eC/Brhw4d0uLFixUfH8+yOgDAj0Ho+7OysjJNmzZNLpdLLpdL06ZN44ZcwGSnbpQ/eaFdQUGB2WMBAHwPoe/P9uzZo9tuu00RERGKiIjQrbfeSugDJjvz/Py8vDy+aQMAXApC358R+kD7cub6/Pj4eP3ud7/TwYMHzR4NAOB7CH1/RugD7UtFRYXmzZun2NhY1ucDAH4sQt+flZaWasqUKQoPD1dYWJgmT56s0tJSs8cC/FZJSYnGjRun4OBg2Ww2ZWVlsT4fAHCpCH1/5na7lZubK4fD4T11p7i42OyxAL+1c+dO70lYQUFBGjVqlIqKisweCwDgmwh9f0boA+3Lqf8/abfblZOTw424AIBLRej7M5buAO2L2+1WXl6e9803J+4AAH4EQt+fsRkXaF92796tCRMmKCQkRCEhIZowYYJ27dpl9lgAAN9E6PszQh9oX9xut3JycmS322W32zV69GiW7gAALhWh789YugO0L4WFhcrOzpbNZlNgYKCys7M5dQcAcKkIfX/GZlygfSkqKtKoUaMUFBTEqTsAgB+L0PdnhD7QvhD6AIA2ROj7M5bu4FJ4PB7t2LFD999/v9LT09WtWzf17t1b8fHx6tixo+Li4jRq1CitWbNGNTU1Zo/rM+rr67V+/XolJyfLMAwuzAIA/FiEvj9jMy5aw+PxyO12a9myZZo0aZLi4+Nlt9tlGMY5H6GhoZo4caJ2795t9vg+Y//+/Zo9e7Y6depE6AMA2gKh788IfZxLXV2d3nvvPf3bv/2bYmNjZbPZzhv2Zz5sNpsyMzOJ1FZqbm7Whx9+qCFDhnj/hsHBwRo/frxKSkrMHg8A4JsIfX9G6ONMx48fV2Fhoe666y5FR0dfMOZDQ0MVGxur3r17Ky4uTsHBwQoICJBhGOrevbuefvppHT582Oyn1e7V1NRo+fLl6tGjhwzDkNPp1KBBg/Tiiy+y/AkAcKkIfX9G6OOkEydOqLy8XHPnzlVcXNxZw95ut6tr166aNGmSli1bJrfbLY/H4/0Z1dXVevrpp5WYmCjDMBQSEqIbb7yRC58uoLGxUe+9956GDQLMzaYAABH2SURBVBumwMBAGYahIUOG6IMPPlBzc7PZ4wEAfBeh788IfUhSVVWVHnvsMSUlJSkoKOgHcZ+cnKwFCxaoqKjotLA/m6KiIo0cOVJBQUEKDAzUddddp8LCwp/omfiexsZGbd26VcOHD/f+7VmyAwBoI4S+PyP0/Vt1dbVeeOEFDRs2TMHBwacFvsvlUl5env7rv/5LtbW1rf6ZZzuy1e12X8Zn4btaWlq0c+dOjRkzxru5OTAwUEOHDtWmTZvU0NBg9ogAAN9G6PszQt8/1dbWasOGDcrPz5fL5Tot8IODgzV06FA9//zzqq6uvuif7Xa7lZeX5w39vLw8Qv8cvvjiC91999266qqrZBiGgoKCNHz4cL333ntqbGw0ezwAgO8j9P1ZWVmZbrnlFrlcLrlcLt1yyy367LPPzB4Ll0FDQ4N2796txYsXKyUlRQ6H47TADwoKUp8+ffTYY4/p0KFDl/x7du/erQkTJigkJEQhISGaMGECa/TP4uuvv9aDDz7o3Q9hs9k0cOBArV27VseOHTN7PACANRD6/sztdisnJ0d2u52bcS2mqalJ+/fv17p163TnnXeqT58+P1iec/IRFxenOXPmqLy8XCdOnPhRv5dP9C/s8OHDeuqpp9S9e3fva9CnTx+9/PLLqqurM3s8AIB1EPr+rKioSCNGjFBgYKCcTqfGjh3LBkAf1dLSopqaGm3ZskX33XefBg8erLCwsPMejxkdHa277rpLhYWFOn78eJvMceoafbvdrpycHO3cubNNfrYV1NXV6eWXX1afPn28r0P37t315JNPcgwpAKCtEfr+qqmpSVu3blV6eroMw1DHjh31m9/8Rvv37zd7NLRSfX29Pv74Yy1evFijRo1Shw4dLniRVUBAgMLCwpSdna0NGza0+TKRUzeXhoWFafLkyfr000/b9Hf4quPHj2vz5s0aMmSI9wKyuLg4Pfjgg6qsrDR7PACA9RD6/qq2tlarVq1SUlKSDMNQUlKSVq1apaNHj5o9Gs6jvr5e27dv18yZMxUbG+u9nOp8Yd+hQweNGjVKixcv1scff6xvv/32ss1XUlKicePGKTg4WDabTVlZWdyOq++/cSksLNTo0aO9J+xcddVVuvvuu/XFF1+YPR4AwJoIfX915mbA9PR0bd26VU1NTWaPhnM4cOCA5s+fry5dupw37sPCwjR48GDdd9992rJli2pqan6yi5cqKio0b948xcbGyjAMpaSk6LXXXrvg+ftWV15erpkzZyoyMtJ7P8GYMWNUVFRk9mgAAOsi9P1VcXGx8vPz5XQ6FRgYqOzsbC42aqeOHj2qP/7xjxo1apRCQ0N/EPbBwcHq27ev7rzzTq1bt0779+837Q2bx+PR66+/rpSUFBmGodjYWM2dO1cVFRWmzNMefPXVV/rtb3+rmJgY71n5aWlp2rBhw2X9dgUA4PcIfX915kbc/Px8TtxpZ7777juVlpbqV7/6laKjo896Y+2iRYu0a9eudnW5UmFhoTIzM2Wz2RQQEKBevXpp2bJlfrnZ9NChQ3rkkUcUHx/vfe369u2rV199lWM0AQCXG6Hvj87ciMuGwPbn8OHDWr58uZKTkxUUFHTaeffJycl65pln2m04FxYWKisry7vh1DAMORwO9e3bV7/5zW/0zjvvqLKy0vLLxGpqarRixQr17NnT+3fo2bOnli9frpqaGrPHAwBYH6Hvj2pra7V69erTNuKuXr2ajbjtwJEjR/THP/5RN9544w9O0YmOjtYvf/lLlZaW6rvvvjN71HPavXu3Jk6ceNZlRicfgYGBcrlcGjx4sJ555hkdOHDgJ9tH8FM4duyYXn31VfXr18/7nOPj47V48eIfdSEZAAAXgdD3RwcPHtTChQu9mzrZiGuuEydOaN++fXryySc1dOjQHwRyaGioRo4cqfXr1/vEm7GamhqtWbNGI0aMUERExGmf7J/rZKCrrrpKN998s9auXauqqiqfjv7GxkZt2bLltGM0Y2JidN999+mrr74yezwAgP8g9P3R3r17NWPGDEVGRrI+30Tffvut/vKXv2jmzJmKiYk561GZXbp00cMPP6wDBw6YPe4lqa2t1dq1a5WVlSWXy2X56G9sbNTWrVt17bXXepdcRUZGasaMGdq7d6/Z4wEA/Auh749KSko0duxYOZ1ORUZGavr06UTIT6i6ulovvfSS8vLyFBERcdbgdTqdys7O1saNGy1zMktTU5MqKyv1zjvvaPbs2erXr5+cTud5wz8qKko33nijnn32WZWWlrbZDb6XQ2Njo7Zv3678/HyFhIR4N02PHj1aBQUFPvWGBQBgCYT+mU4uOxgzZowSExOVmJiorl27KiEhQampqcrMzNSIESNOe2RmZio1NVXdunU773938pGVlaX09HR1795dvXv3Vn5+vn75y19q0aJFWrlypTZs2KDt27ertLRUhw4dUkNDg1paWtrk+Z3ciJuRkeH9xHjBggU++4mxrzh+/LhKSkr0wAMPqG/fvnI4HD+IWrvdrj59+uiBBx5QSUlJu47attDc3KyqqiqtXbtWN998s6666qrzXgAWEhKi5ORk/eIXv9Abb7yhgwcP6sSJE2Y/DUlSXV2d1q5dq4yMDO8n+UFBQRo8eLBee+01y7xZAwD4FEL/TJ9++qluuumm824k/KkfgYGBioiIUKdOnRQTE6OkpCSNHz9ev/71r7V06VKtXr1amzZtUkFBgfbt26e6urpzBtDJG3H79Onj3YjLjbiXzz/+8Q9t3rxZI0eOVHBw8Flf34iICOXm5urll19WdXW12SOb4mKj3zAMuVwuZWVlaf78+frggw9UX19vyuw1NTV67rnnlJSU5J05JCRE+fn52r59uxobG02ZCwDg9wj9M7XH0L/Yh81mU2hoqK6++mp17NhRCQkJSk9PV1ZWloYNG6Zu3bp5l0ywEffyORmAvXv3/kG0BgQEKDY2VjNnztRf/vIXPvE9RXNzsw4ePKjly5dr0KBBCg0NvWD0BwQEKDo6WuPHj9ejjz6qjz766CcJ/4qKCs2dO9d7E/DJN+bXXHONtm7dSuQDAMxE6J/pp1q6k5aWpoSEBHXq1EkdO3ZURESEAgMDf/JvCrgR9/I4WwAaxvcn6AwdOlRPPvmk9u3b126WnrRn9fX1+vDDDzV//nxlZ2efc1/DmeF/5ZVXKjs7W3PmzNHbb7+tAwcOtNmxpB6PR2+//bYyMzNPW4blcDh07bXX6s033+TNGwDAbIR+e9HS0qKGhgYdOnRIpaWl2r59uzZs2KCVK1dq0aJF+tWvfqVx48apd+/eiomJUXR0tMLCwi54isn5HiEhIbrhhhu0a9cus5++pbjdbuXl5Z0WgHa7XampqXr++ee5LOlHOHHihA4dOqQ33nhDd999t5KTk70bXy/0CA0N1YABAzRjxgytWbNGe/bsueR9ELt379aECRNO+90xMTGaM2eOKioq2vhZAwBwSQh9X3bixAnV1dVp3759Kigo0KZNm7R69WotXbpUs2bN0tixY5WUlKTu3bt7l+5kZ2crIyNDPXr0UH5+vt544w3V1dWZ/VQsobGxUe+9956uueaa026zPRmAnKHe9o4fP67S0lItWbJEaWlpCg8Pb/WbX5vNpvDwcA0aNEjPPvtsqy/tamxs1ObNm5WRkeFdUtSzZ0+tWLGCN3EAgPaE0G8LHo9Hf/3rX7Vw4UItXLhQH330kTwej9lj4Sd08vz04cOHeyM/ICBASUlJeu655wjAn0hDQ4P27NmjNWvWaMaMGRowYECr9tu09vz+2tpavfTSS0pLS/O+znwzBgBopwj9tvDFF1/oF7/4haKiohQQEKCgoCA5nU6FhYUpNjZWycnJGjBggHcNf+/evdWtWzcNGjRIDz30kHbv3s2mPR/l8Xi0efNmjR49+rSgDAoKUkZGhtauXcs3Jib67rvvdPDgQb399tuaO3eusrOzdeWVV15wc290dLR+/vOfa/Pmzae9frt27dL48eO9JygFBQUpLS1NL7/8smpra018pgAA/ACh3xbcbrdyc3PPejZ6ax5hYWEaNGiQ7r//fv35z38mDH3EkSNH9Pzzz6tv376nLRcJCgrS8OHDOXWlnaqvr9df//pX/fu//7uSk5PPe2lXZGSkpk6dqi1btujYsWMqLCxUZmambDabAgIClJGRoc2bN/M6AwDaI0K/LbTlkZwnlxBcd911mj59uhYtWqSXXnpJ77//vsrLy3Xs2DFu2DRZc3OzCgsLlZ+f/4Oz8W02mwYOHKh169aZdq47Wq+15/fbbDY5HA45HA7vmzqbzaasrCwVFBSY/TQAADgbQr8t1NTUaNWqVbr22mt1xRVXyOl0Kjg4+JxLd3r16qWYmBgFBwdfcAnBmY+goCBFREQoOjpa/fv31x133KFnn31Wf/rTn7Rv3z4+WbzMjhw5ot///vfq06fPD167kJAQZWdna926dSzj8EHNzc06dOiQVqxYobS0tAt+Q+dwOJSXlye322326AAAnA2hb6a6ujr9+c9/1v3336/BgwcrLCzsR38b4HQ6FRkZqYEDB+ree+/V22+/rcrKSi7E+pHq6+v17rvv6pZbblFkZORpf3en06mRI0fq3XffVUNDg9mjog2c7/U++ZpnZWXpv//7v/nmBgDQXhH67UVjY6PKysq0ePFiDRo0SNHR0YqIiDjtmMZLfTidTiUmJmrixIlatGiRNm/eTPy30smLka677rofLNMJCAhQnz599Pvf/15Hjhwxe1RcBtXV1Vq5cqWGDh2qiIgIuVwuDRkyRCtXrtThw4fNHg8AgPMh9Nuz5uZmHTt2TOXl5Xr//ff10ksvaeHChZo6dar69u2ryMhIOZ3Oi17+Yxjf34rrcrmUnp6uRx99VHv27GHZzxmqqqq0ZMkSJSQknPXNE5/iAwCAdozQ93WNjY3at2+f/vSnP2n58uX613/9V/Xr108REREKDAzk5J+L5PF4VFRUpIceekj9+vWT3W4/7yksAAAA7RShb1VNTU2qrKzU5s2btWjRIk2cOFGJiYnnPUrw1CUpV155pdLS0ry36Y4YMeKcj8zMTKWmpno3G6empiozM/O8/+ZSf87F/q4z//uUlJTTNkaf/PdZWVlKT09XVFTUWW9WdTgcGjx4sF555RU22gIAAF9A6PuTk/G/adMmzZ49W8nJyQoNDb2kpT/+9IiPj9eSJUtUVVVl9ksIAADQWoS+v2vrk3+s8rDZbHK5XBo1apTefPNNffvtt2a/VAAAABeD0Mf/a2xs1KeffqqHH35YgwYNUmJiogYPHuwXS3cGDx6sHj16KD09Xb/97W+1Y8cOeTwes18SAACAS0XoAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAWROgDAAAAFkToAwAAABZE6AMAAAAW9J+GpDfNngIAAABAm1phSHrN7CkAAAAAtKn5hqSfS/rC5EEAAAAAtI1qSTcZksIkPS3pqLnzAAAAAPiRPJIWS4ozTpI0VtKr+v4dAAAAAADf8ZWkP0maICnMMAzjfwEU5ZzqU9Ki3QAAAABJRU5ErkJggg==";
  
}

function erase (e) {
  // Might have to clean up? Can't access canvas or context from outside?
  // Canvas removes drawings when resized, not sure why, not erase fuctions fault
  const canvas = document.querySelector('#canvas');
  const context = canvas.getContext('2d');
  context.fillStyle = "white";
  context.fillRect(0, 0, window.innerWidth*(1/3), window.innerHeight*(1/3));
}

$(function () { 
  $("#screenshot").click(function() { 
    html2canvas($("#canvas"), { 
      onrendered: function(canvas) { 
        var imgsrc = canvas.toDataURL("image/png");
        console.log(imgsrc); 
        $("#newimg").attr('src', imgsrc); 
        $("#img").show(); 
        var dataURL = canvas.toDataURL();
        var hidden = document.getElementById("form:dataURL");
        hidden.value = dataURL;
        document.getElementById("form:subbtn").click();
        $.ajax({ 
          type: "POST", 
          url: "script.php", 
          data: { 
              imgBase64: dataURL 
          } 
        }).done(function(o) { 
          console.log('saved');
        }); 
      } 
    }); 
  }); 
}); 


