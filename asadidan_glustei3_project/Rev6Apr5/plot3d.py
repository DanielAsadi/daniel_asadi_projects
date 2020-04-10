import matplotlib.pyplot as plt

num_elements = 16
X = [0] * num_elements
Y = [0] * num_elements
Z = [0] * num_elements
i = 0

filepath = 'data/avg_t3.txt'
file = open(filepath, 'r')
arr = file.readlines()
for line in arr:
    line = line.split()
    X[i] = float(line[0])
    Y[i] = float(line[1])
    Z[i] = float(line[2])
    i += 1

print(X)
print(Y)
print(Z)

# x = np.reshape(X, (1, 16))
# y = np.reshape(Y, (1, 16))
# z = np.reshape(Z, (1, 16))

fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')
ax.plot_trisurf(X, Y, Z)

ax.set_title('N Vs. K Vs. Average time per game (s)')
ax.set_xlabel('N')
ax.set_ylabel('K')
ax.set_zlabel('Average time per game (s)')

plt.show()
