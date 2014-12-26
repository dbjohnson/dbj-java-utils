package com.BryanJohnson.Utils.Math;

import java.util.List;
import java.util.Random;

import com.BryanJohnson.Utils.Sorting.QuickSort;
import com.BryanJohnson.Utils.Sorting.SortSupport;

// Adds basic statistical operations to a double / int array 

public class StatsArray {
    
    protected String m_description;
    protected double [] m_values;
    protected double m_sum;
    protected double m_sumSq;
    protected int m_numValues;    

    public StatsArray(int length)
    {
        this(length, "(no name)");
    }
    
    public StatsArray(int length, String _name)
    {
        m_description      = _name;
        m_values    = new double [length];
        m_numValues = 0;
        calcSums();
    }    
    
    public StatsArray(int [] _integers)
    {
        this(_integers, "(no name)");
    }
    
    public StatsArray(int [] _integers, String _name)
    {
        m_description   = _name;
        m_values = new double [_integers.length]; 
        for (int i = 0; i < _integers.length; i++) {
            m_values[i] = _integers[i];
        }
     
        m_numValues = m_values.length;
        
        calcSums();
    }    
    
    public StatsArray(double [] _doubles)
    {
        this(_doubles, "(no name)");
    }

    public StatsArray(double [] _doubles, String _name)
    {
        m_description      = _name;
        m_values    = _doubles.clone();
        m_numValues = _doubles.length;
        
        calcSums();
    }
    
    public StatsArray(List<Double> _doubles, String _name) {
        m_description      = _name;
        m_numValues = _doubles.size();


        m_values = new double [_doubles.size()];
        for (int i = 0; i < _doubles.size(); i++) {
            m_values[i] = _doubles.get(i);
        }

        calcSums();
    }
    
    public StatsArray clone() {
        StatsArray clone = new StatsArray(this.size(), this.getDescription());
        
        for (int i = 0; i < this.size(); i++) {
            clone.add(this.get(i));
        }
        
        clone.calcSums();
        
        return clone;
    }
    
    public StatsArray combineWith (StatsArray that) {
        int numVals = this.size() + that.size();
        double [] newValues = new double[numVals]; 
        
        for (int i = 0; i < this.size(); i++) {
            newValues[i] = this.get(i);
        }
        
        int offset = this.size();
        for (int i = 0; i < that.size(); i++) {
            newValues[i+offset] = that.get(i);
        }
        
        m_values  = newValues;
        m_numValues = numVals;
        calcSums();
        
        return this;
    }
    
    public static double [] selectRandomSamples(double [] vec, int numSamples) {
        double [] samples = new double[numSamples];
        int [] shuffleOrder = SortSupport.getRandomizedSortOder(vec.length);
        
        for (int i = 0; i < numSamples; i++) {
            samples[i] = vec[shuffleOrder[i]];
        }
        return samples;
    }
    
    public double [] selectRandomSamples(int numSamples) {
        return selectRandomSamples(m_values, numSamples);
    }
    
    public int size() {
        return m_numValues;
    }
    
    public boolean isFull() {
    	return m_numValues == m_values.length;
    }
    
    public void add(int val) {
    	add ((double) val);
    }
    
    public void add(double val) {
    	if (Double.isNaN(val) || Double.isInfinite(val)) {
    	    System.err.println("StatsArray cannot accept NaNs or Infs");
    	    return;
    	}
        
    	if (!isFull()) {
        	//  add new value to end of list
        	m_values[m_numValues++] = val;
            m_sum   += val;
            m_sumSq += val*val;    	
    	}
    	else {
    		throw new Error("StatsArray full!"); 
//    		double [] temp = m_values.clone();
//    		m_values = new double[m_numValues];
//    		
//    		for (int i = 0; i < temp.length; i++) {
//    			m_values[i] = temp[i];
//    		}
//            m_sum   += val;
//            m_sumSq += val*val;    	
    	}
    	
        
    }

    public void set(int i, int val)
    {
        set(i, (double) val);
    }

    public void set(int i, double val)
    {
        if (i >= m_values.length) {
        	throw new Error("Out of bounds!");
        }

        m_sum   -= m_values[i];
        m_sumSq -= m_values[i]*m_values[i];
        
        m_values[i] = val;
        
        m_sum   += val;
        m_sumSq += val*val;

        if (i + 1 > m_numValues) {
            m_numValues = i + 1;
        }
    }
    
    public void set(int startIdx, double [] vals)
    {
        for (int i = 0; i < vals.length; i++) {
            set(startIdx + i, vals[i]);
        }
    }

    public void set(int startIdx, int [] vals)
    {
        for (int i = 0; i < vals.length; i++) {
            set(startIdx + i, vals[i]);
        }
    }
    
    public double get(int i)
    {
        return m_values[i];
    }
    
    protected void calcSums()
    {
        m_sum = 0;
        m_sumSq = 0;
        for (int i = 0; i < m_numValues; i++) {
            m_sum   += m_values[i];
            m_sumSq += m_values[i]*m_values[i];
        }
    }

    public double getSum()
    {
        return m_sum;
    }

    public double getMean()
    {
        if (m_numValues == 0) {
            return 0;
        }
        return m_sum/m_numValues;
    }
  
    public double getStd() 
    {
        return Math.sqrt(getVar());
    }

    public double getVar() {
        return getVar(true);
    }

    public double getVar(boolean normalizeByNminus1)
    {
        if (size() == 0) {
            return 0;
        }
        
        double var = (m_sumSq/size() - getMean()*getMean());
        
        if (normalizeByNminus1) {
             var *= (double) size() / (size()-1);
        }
        
        return var;
    }    
  
    public double getRange()
    {
        return (getMax() - getMin());
    }
    
    public double getMax()
    {
        double max = -Double.MAX_VALUE;
        
        for (int i = 0; i < m_numValues; i++) {
            max = Math.max(max, m_values[i]);
        }
        
        return max;
    }    


    public double getMin() 
    {
        double min = Double.MAX_VALUE;
        
        for (int i = 0; i < m_numValues; i++) {
            min = Math.min(min, m_values[i]);
        }
        return min;
    }
    
    public double [] getSortedValues() {
        double [] sorted= new double [size()];
        for (int i = 0; i < size(); i++) {
            sorted[i] = m_values[i];
        }
        
        QuickSort.sort(sorted);
        
        return sorted;
    }
    
    public double getMedian() {
        
        double [] sorted = getSortedValues();
    	if (sorted.length % 2 == 0) {
    		return (sorted[sorted.length/2] + sorted[sorted.length/2-1])/2;
    	}
    	else {
    		return sorted[sorted.length/2-1];
    	}
    	
    }
    
    public double getPrctile(int prcTile) {
    	return getPrctiles(new int [] {prcTile})[0];
    }
    
    public double [] getPrctiles(int [] prcTiles) {
        
        // make copy of values array so we won't corrupt it by sorting
        double [] samples = getSortedValues();
        
        double [] prcTileValues = new double[prcTiles.length];
        if (size() == 0) {
            System.err.println("Warning - StatsArray is empty, returning NaNs");
            for (int i = 0; i < prcTiles.length; i++) {
                prcTileValues[i] = Double.NaN;
            }
        }
        else {
            for (int i = 0; i < prcTiles.length; i++) {
                prcTileValues[i] = samples[prcTiles[i]*(size()-1)/100];
            }
        }
        
        return prcTileValues;
    }
    
    public double getPrctile(double prcTile) {
        if (size() == 0) {
            System.err.println("Warning - StatsArray is empty, returning NaN");
            return Double.NaN;
        }
        
        // make copy of values array so we won't corrupt it by sorting
        double [] samples = new double [size()];
        for (int i = 0; i < size(); i++) {
            samples[i] = m_values[i];
        }
        
        QuickSort.sort(samples);
        return samples[(int)(prcTile*(size()-1)+0.5)];
    }
    
    public void setDescription(String desc) {
        m_description = desc; 
    }

    public String getDescription() {
        return m_description; 
    }

    public void subtractMean() {
        double mean = getMean();
                
        for (int i = 0; i < size(); i++) {
            m_values[i] -= mean;
        }
        
        calcSums();
    }
    
   
    
    public double innerProd(StatsArray that)
    {
        if (this.size() != that.size()) {
            return 0.0;
        }        

        double innerProd = 0;
        for (int i = 0; i < size(); i++) {
            innerProd += this.get(i) * that.get(i);
        }
        
        return innerProd;
    }
    
    public double corrCoefSq(StatsArray that)
    {
        
        if (this.size() != that.size()) {
            return 0.0;
        }
        
        int n = this.size();

        double innerProdCorrectedN = innerProd(that) - n * this.getMean() * that.getMean();
        double denom = this.getVar(false) * that.getVar(false) * n * n;

        
        if (denom == 0.0) {
            return 0.0;
        }
        else {
//            if (innerProdCorrectedN * innerProdCorrectedN / denom > 1.001 || innerProdCorrectedN * innerProdCorrectedN / denom < -1.001) {
//                System.err.println("oops: " + (innerProdCorrectedN * innerProdCorrectedN / denom));
//                this.print();
//                that.print();
//            }
            
            return Math.signum(innerProdCorrectedN) * innerProdCorrectedN * innerProdCorrectedN / denom;
        }
    }
    
    public double corrCoef(StatsArray that) 
    {
        double corrSq = corrCoefSq(that);
        return Math.sqrt(Math.abs(corrSq)) * Math.signum(corrSq);
    }
    
    
    public double combinedVar(StatsArray that) {
        int n1    = this.size();
        double x1 = this.getMean();
        double s1 = this.getVar();
        int n2    = that.size();
        double x2 = that.getMean();
        double s2 = that.getVar();
        
        
        double xc = (x1 * n1 + x2 * n2) / (n1 + n2);

        double num = (n1-1)*s1 + n1*(x1-xc)*(x1-xc) + (n2-1)*s2 + n2*(x2-xc)*(x2-xc);
        double den = n1 + n2 - 1;
        
        return num / den;
    }
    
    public double mahalDistSq(StatsArray that) 
    {
        double meanDiff = this.getMean() - that.getMean();
        
        return (meanDiff*meanDiff) / combinedVar(that);
    }

    public double mahalDist(int val) {
    	return mahalDist((double) val);
    }
    
    public double mahalDist(double val) {
    	return (val - getMean()) / getStd();
    }
    
    public double [] getData() {
        return m_values;
    }

    
    public double pValIsUniform(double rangeMin, double rangeMax) 
    {
        
        double [] sorted = new double[size()];
        for (int i = 0; i < size(); i++) {
            sorted[i] = get(i);
        }
        
        QuickSort.sort(sorted);
        
        double [] ecdf = new double[size()];
        double [] F    = new double[size()];
        
        
        for (int i = 0; i < sorted.length; i++) {
            ecdf[i] = (double)(i+1) / sorted.length;
            F[i]    = (sorted[i] - rangeMin) / (rangeMax - rangeMin);
        }
        
        return ksTest(ecdf, F);
    }    
    
    public boolean isUniform(double pVal, double rangeMin, double rangeMax) 
    {
        // if true, cannot reject hypothesis that distributions are same
        return pValIsUniform(rangeMin, rangeMax) > pVal;
    }
    
    private double ksTest(double [] ecdf, double [] F) {
        // Kolmogorov–Smirnov test that an empirical distribution ecdf  
        // was generated from underlying distribution F
        int n = ecdf.length;
        
        // find maximum absolute difference between ECDF and F
        double Dplus = ecdf[0]-F[0];
        double Dminus = F[0];
        for (int i = 1; i < n; i++) {
            Dplus  = Math.max(Dplus, ecdf[i]-F[i]);
            Dminus = Math.max(Dminus, F[i] - ecdf[i-1]);
        }
        
        double Dn = Math.max(Dplus, Dminus);
        
        // test statistic
        double T = Math.sqrt(n) * Dn;

        // Calculate Pr(x <= T) using 
        // asymptotic approximation of Kolmogorov CDF value 
        double pr_x_lte_T = 0;
        double maxIter = 100;
        
        // constants
        double k1 = Math.sqrt(2*Math.PI) / T;
        double k2 = Math.PI * Math.PI / (8*T*T);

        for (int i = 1; i < maxIter; i++) {
            double incrementalP = k1 * Math.exp(-(2*i-1)*(2*i-1)*k2);
            
            // we've reached the limits of double precision
            if (pr_x_lte_T == pr_x_lte_T + incrementalP) {
                break;
            }
            
            pr_x_lte_T += incrementalP;
        }
        
        
        // Pr(x > t) == 1 - Pr(x <= T)
        double pr_x_gt_T = 1 - pr_x_lte_T; // upper tail test

        return pr_x_gt_T;
        
//        Alternative calculation of p value from "Numerical Recipes" (3rd edition)
//        Somewhat cheaper, but approximation here is explicit, doesn't provide much advantage
//        
//        double pVal = 0;
//        if (T < 1.18) {
//            double y = Math.exp(-1.23370055013616983/(T*T)); // exp(-pi*pi/(8*z*z)
//            pVal = 1.0 - 2.25675833419102515*Math.sqrt(-Math.log(y))*(y + Math.pow(y, 9) + Math.pow(y,25) + Math.pow(y, 49));
//        }
//        else {
//            double x = Math.exp(-2*T*T);
//            pVal = 2.0*(x - Math.pow(x, 4) + Math.pow(x, 9));
//        }        
    }

    
    public boolean ksTest(double [] ecdf, double [] F, double pVal) 
    {
        // if true, cannot reject hypothesis that distributions are same
        return ksTest(ecdf, F) > pVal; 
    }
    
    public double [] getBootstrapMeans(int n) 
    {
        Random r = new Random(123);
        double [] means = new double[n];
        
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int s = 0; s < size(); s++) {
                int idx = r.nextInt(size());
                sum += get(idx);
            }
            
            means[i] = sum/size();
         }
        
        return means;
    }
    
    public double [] getDifferences() {
        double [] diffs = new double[m_numValues - 1];
        double lastVal = m_values[0];
        
        for (int i = 0; i < diffs.length; i++) {
            double nextVal = m_values[i+1];
            diffs[i] = nextVal - lastVal;
            lastVal = nextVal;
        }
        
        return diffs;
    }
    
    
    public void clear() {
        for (int i = 0; i < m_numValues; i++) {
            m_values[i] = 0;
        }
        m_numValues = 0;
        calcSums();
    }
    
    
    
    
}
