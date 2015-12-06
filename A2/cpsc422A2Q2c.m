function cpsc422A2Q2c()
    sample = csvread('lw_1.csv');
    [N,D] = size(sample);
    
    x = 1:N;
    y = zeros(N,1);
    totalWeight = 0;
    curentWeight = 0;
    
    for i=1:N
        result = sample(i,1);
        weight = sample(i,2);
        
        if result == 1
            curentWeight = curentWeight + weight;
            totalWeight = totalWeight + weight;
        elseif result == 2
            totalWeight = totalWeight + weight;
        end
         y(i,1) = (curentWeight/totalWeight);
    end
    semilogx(x, y);
    disp(y(N,1));
end

